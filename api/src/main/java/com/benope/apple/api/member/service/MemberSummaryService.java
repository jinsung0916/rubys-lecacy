package com.benope.apple.api.member.service;

import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface MemberSummaryService {

    MemberSummaryComposite getMemberSummaries(@NotNull Long mbNo);

}
