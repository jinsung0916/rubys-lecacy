package com.benope.apple.api.term.bean;

import com.benope.apple.config.webMvc.EnumResponse;
import com.benope.apple.domain.member.bean.MemberTermAgree;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class MemberTermAgreeResponse {

    private EnumResponse termCd;
    private String termVersion;
    private boolean isAgree;
    private LocalDate agreeDate;

    public static MemberTermAgreeResponse fromEntity(MemberTermAgree entity) {
        return MemberTermAgreeResponse.builder()
                .termCd(EnumResponse.fromEntity(entity.getTermCd()))
                .termVersion(entity.getTermVersion())
                .isAgree(entity.isAgree())
                .agreeDate(entity.getTermAgreeDt())
                .build();
    }

}
