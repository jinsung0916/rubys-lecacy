package com.benope.apple.api.scrap.controller;

import com.benope.apple.api.scrap.bean.*;
import com.benope.apple.api.scrap.service.ScrapHelperService;
import com.benope.apple.api.scrap.service.ScrapService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.utils.PagingMetadata;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ScrapController {

    private final ScrapService scrapService;
    private final ScrapHelperService scrapHelperService;

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.scrap")
    public ApiResponse getList(
            @RequestBody @Valid ScrapSearchRequest input
    ) {
        Page<Scrap> page = scrapService.getList(input.toEntity(), input.toPageable());

        List<Object> response = page
                .stream()
                .map(it -> {
                    if (it.isScrap()) {
                        return ScrapResponse.fromEntity(it);
                    } else {
                        return DirectoryResponse.fromEntity(it);
                    }
                })
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

    @PostMapping("/reg.scrap")
    public ApiResponse registScrap(
            @RequestBody @Validated({Default.class, Create.class}) ScrapRequest input
    ) {
        Scrap scrap = scrapService.registScrap(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(ScrapResponse.fromEntity(scrap));
    }

    @PostMapping("/mod.scrap")
    public ApiResponse modifyScrap(
            @RequestBody @Validated({Default.class, Update.class}) ScrapRequest input
    ) {
        Scrap scrap = scrapService.updateScrap(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(ScrapResponse.fromEntity(scrap));
    }

    @PostMapping("/mod.scrap.multiple")
    public ApiResponse modifyScrapMultiple(
            @RequestBody @Validated({Default.class, Update.class}) ScrapRequestComposite input
    ) {
        scrapHelperService.updateMultipleScrap(input.toEntity());
        return RstCode.SUCCESS.toApiResponse();
    }

    @PostMapping("/del.scrap")
    public ApiResponse deleteScrap(
            @RequestBody @Validated({Default.class, Delete.class}) ScrapRequest input
    ) {
        scrapHelperService.unScrap(SessionUtil.getSessionMbNo(), input.getFeedNo());
        return RstCode.SUCCESS.toApiResponse();
    }

}
