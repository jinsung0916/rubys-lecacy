package com.benope.apple.api.term.service;

import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.domain.term.bean.Term;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Validated
public interface TermService {

    List<Term> getList(@NotNull Term term);

    Optional<Term> getByTermCd(@NotNull Term.TermCd termCd);

    void requireMandatoryTerms(List<MemberTermAgree> memberTermAgrees);

}
