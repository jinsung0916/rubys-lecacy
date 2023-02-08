package com.benope.apple.api.auth.serviceImpl;

import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.bean.TokenResponse;
import com.benope.apple.api.auth.service.LoginService;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class LoginServiceComposite implements LoginService {

    private final List<LoginService> loginServices;

    @Override
    public boolean isApplicable(LoginRequest loginRequest) {
        return true;
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        return DomainObjectUtil.nullSafeStream(loginServices)
                .filter(it -> it.isApplicable(loginRequest))
                .map(it -> it.login(loginRequest))
                .findFirst()
                .orElseThrow();
    }


}
