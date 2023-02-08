package com.benope.apple.admin.member.service;

import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
public interface MemberSummaryService {

    MemberSummaryComposite getMemberSummaries(@NotNull Long mbNo, LocalDate startDate, LocalDate endDate);

}
