package com.benope.apple.api.appVersion.controller;

import com.benope.apple.api.appVersion.bean.AppVersionRequest;
import com.benope.apple.api.appVersion.bean.AppVersionResponse;
import com.benope.apple.api.appVersion.service.AppVersionService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class AppVersionController {

    private final AppVersionService appVersionService;

    @PostMapping("/get.app.version")
    public ApiResponse getOne(
            @RequestBody @Valid AppVersionRequest input
    ) {
        AppVersion version = appVersionService.getList(input.toEntity(), input.toPageable())
                .stream()
                .findFirst()
                .orElse(null);

        return RstCode.SUCCESS.toApiResponse(Objects.nonNull(version) ? AppVersionResponse.fromEntity(version) : null);
    }

}
