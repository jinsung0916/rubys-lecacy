package com.benope.apple.api.auth.event;

import com.benope.apple.api.auth.service.LoginHistoryService;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.loginHistory.event.RefreshTokenAccessEvent;
import com.benope.apple.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class RefreshTokenAccessEventListener {

    private final LoginHistoryService loginHistoryService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(RefreshTokenAccessEvent e) {
        LoginHistory loginHistory = e.getSource();

        LoginHistory updateRequest = LoginHistory.builder()
                .loginHistoryNo(loginHistory.getLoginHistoryNo())
                .lastAccessAt(DateTimeUtil.getCurrentDateTime())
                .build();

        loginHistoryService.update(updateRequest);
    }

}
