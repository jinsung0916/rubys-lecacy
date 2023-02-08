package com.benope.apple.admin.appVersion.service;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface AppVersionHelperService {

    boolean isExist(@NotNull AppVersion appVersion);

}
