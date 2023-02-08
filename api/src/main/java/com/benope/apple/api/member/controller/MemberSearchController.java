package com.benope.apple.api.member.controller;

import com.benope.apple.api.member.bean.MemberSimpleResponse;
import com.benope.apple.api.member.bean.MemberSolrSearch;
import com.benope.apple.api.member.service.MemberSearchService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.member.bean.MemberSolrEntity;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberSearchController {

    private final MemberSearchService memberSearchService;

    @PostMapping("/search.member.by.nickname")
    public ApiResponse searchMemberByNickname(
            @RequestBody @Valid MemberSolrSearch input
    ) {
        Page<MemberSolrEntity> page = memberSearchService.search(input.toEntity(), input.toPageable());

        List<MemberSimpleResponse> result = page.getContent()
                .stream()
                .map(MemberSimpleResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

}
