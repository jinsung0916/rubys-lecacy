package com.benope.apple.api.auth.service;

import com.benope.apple.api.auth.bean.JoinRequest;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface JoinService {

    void join(@NotNull JoinRequest joinRequest);

}
