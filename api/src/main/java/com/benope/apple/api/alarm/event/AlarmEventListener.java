package com.benope.apple.api.alarm.event;

import com.benope.apple.api.alarm.service.AlarmService;
import com.benope.apple.api.auth.service.RefreshTokenService;
import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.domain.alarm.bean.Alarm;
import com.benope.apple.domain.alarm.event.AlarmRegistEvent;
import com.benope.apple.domain.alarm.event.AlarmViewEvent;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAlarmAgree;
import com.benope.apple.utils.EntityManagerWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class AlarmEventListener {

    private final EntityManagerWrapper em;

    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final AlarmService alarmService;

    private final ObjectMapper objectMapper;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(AlarmRegistEvent event) {
        Alarm alarm = event.getSource();

        if (!isAlarmAgree(alarm)) {
            return;
        }

        List<Message> messages = toFirebaseMessages(alarm);

        if (!messages.isEmpty()) {
            sendFcm(messages);

            alarm.setPush();
            alarmService.update(alarm);
        }
    }

    private boolean isAlarmAgree(Alarm alarm) {
        Long toMbNo = alarm.getToMbNo();
        MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd = alarm.getAlarmTypeCd().getAlarmAgreeCd();

        return getById(toMbNo)
                .getMemberAlarmAgree(alarmAgreeCd)
                .map(MemberAlarmAgree::isAgree)
                .orElse(false);
    }

    private Member getById(Long mbNo) {
        return memberService.getOne(
                        Member.builder()
                                .mbNo(mbNo)
                                .build()
                )
                .orElseThrow();
    }

    private List<Message> toFirebaseMessages(Alarm alarm) {
        Alarm refreshed = alarmService.getById(alarm.getAlarmNo()).orElseThrow();

        return refreshTokenService.getFcmTokensByMbNo(alarm.getToMbNo())
                .stream()
                .map(it -> toMessage(it, refreshed))
                .collect(Collectors.toUnmodifiableList());
    }

    private Message toMessage(String token, Alarm alarm) {
        return Message.builder()
                .setToken(token)
                .putData("data", toData(alarm))
                .setNotification(toNotification(alarm))
                .build();
    }

    private String toData(Alarm alarm) {
        try {
            return objectMapper.writeValueAsString(Map.of(
                    "alarm_target_cd", alarm.getAlarmTargetDetailCd(),
                    "object_no", alarm.getObjectNo()
            ));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Notification toNotification(Alarm alarm) {
        return Notification.builder()
                .setTitle("Rubys")
                .setBody(alarm.toPushContent())
                .build();
    }

    private void sendFcm(List<Message> messages) {
        try {
            FirebaseMessaging instance = FirebaseMessaging.getInstance();
            instance.sendAll(messages);
        } catch (FirebaseMessagingException e) {
            log.error("", e);
        }
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(AlarmViewEvent e) {
        Alarm alarm = e.getSource();
        if (!alarm.isRead()) {
            Alarm updateRequest = Alarm.builder()
                    .alarmNo(alarm.getAlarmNo())
                    .build();
            updateRequest.setRead();
            alarmService.update(updateRequest);
        }
    }

}
