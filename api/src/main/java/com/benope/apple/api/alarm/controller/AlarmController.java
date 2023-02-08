package com.benope.apple.api.alarm.controller;

import com.benope.apple.api.alarm.bean.AlarmRequest;
import com.benope.apple.api.alarm.bean.AlarmResponse;
import com.benope.apple.api.alarm.service.AlarmService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.alarm.bean.Alarm;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.alarm")
    public ApiResponse getList(
            @RequestBody @Valid AlarmRequest request
    ) {
        Page<Alarm> page = alarmService.getList(request.toEntity(), request.toPageable());

        List<AlarmResponse> responses = page.stream()
                .map(AlarmResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(responses, PagingMetadata.withPage(page));
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.alarm.count")
    public ApiResponse getCount(@RequestBody AlarmRequest request) {
        Long count = alarmService.getCount(request.toUnreadEntity());
        return RstCode.SUCCESS.toApiResponse(Map.of("count", count));
    }

}
