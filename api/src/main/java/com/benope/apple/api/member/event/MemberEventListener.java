package com.benope.apple.api.member.event;

import com.benope.apple.api.member.service.MemberService;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSolrEntity;
import com.benope.apple.domain.member.event.MemberDeleteEvent;
import com.benope.apple.domain.member.event.MemberRegistEvent;
import com.benope.apple.domain.member.event.MemberUpdateEvent;
import com.benope.apple.domain.member.repository.MemberSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberEventListener {

    private final MemberService memberService;

    private final MemberSolrRepository memberSolrRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(MemberRegistEvent e) {
        Member member = e.getSource();
        memberSolrRepository.save(MemberSolrEntity.fromEntity(member));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(MemberUpdateEvent e) {
        Member member = e.getSource();
        Member refreshed = getRefreshed(member);

        MemberSolrEntity entity = MemberSolrEntity.fromEntity(refreshed);
        memberSolrRepository.save(entity);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(MemberDeleteEvent e) {
        Member member = e.getSource();
        String id = String.valueOf(member.getMbNo());
        memberSolrRepository.deleteById(id);
    }

    private Member getRefreshed(Member member) {
        return memberService.getOne(
                        Member.builder()
                                .mbNo(member.getMbNo())
                                .build()
                )
                .orElseThrow();
    }

}
