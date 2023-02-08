package com.benope.apple.api.term.service;

import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.domain.term.bean.Term;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface MemberTermAgreeService {

    List<MemberTermAgree> getByMbNo(@NotNull Long mbNo);

    void agree(@NotNull Long mbNo, @NotNull Term.TermCd termCd, @NotBlank String termVersion);

    void disagree(@NotNull Long mbNo, @NotNull Term.TermCd termCd, @NotBlank String termVersion);

}
