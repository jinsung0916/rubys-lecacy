package com.benope.apple.api.term.controller;

import com.benope.apple.api.term.bean.TermRequest;
import com.benope.apple.api.term.bean.TermResponse;
import com.benope.apple.api.term.service.TermService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.term.bean.Term;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TermController {

    private final TermService termService;

    @PostMapping("/get.term")
    public ApiResponse getTerm(@RequestBody @Valid TermRequest request) {
        List<TermResponse> result = termService.getList(request.toEntity())
                .stream()
                .map(TermResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result);
    }

    @PostMapping("/get.marketing.term")
    public ApiResponse getMarketingTerm() {
       Term term = termService.getByTermCd(Term.TermCd.TERM04)
               .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        return RstCode.SUCCESS.toApiResponse(TermResponse.fromEntity(term));
    }

}
