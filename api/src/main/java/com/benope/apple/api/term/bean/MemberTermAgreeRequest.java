package com.benope.apple.api.term.bean;

import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.domain.term.bean.Term;
import com.benope.apple.utils.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTermAgreeRequest {

    @NotNull
    private Term.TermCd termCd;
    @NotBlank
    private String termVersion;
    @JsonProperty("agree")
    private boolean isAgree;

    public MemberTermAgree toEntity() {
        return MemberTermAgree.builder()
                .termCd(termCd)
                .termVersion(termVersion)
                .termAgreeYn(isAgree ? "Y" : "N")
                .termAgreeDt(DateTimeUtil.getCurrentDate())
                .build();
    }
}
