package com.benope.apple.domain.loginHistory.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;

public class RefreshTokenAccessEvent extends BenopeEvent<LoginHistory> {
    public RefreshTokenAccessEvent(LoginHistory source) {
        super(source);
    }
}
