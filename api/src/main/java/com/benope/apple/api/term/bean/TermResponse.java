package com.benope.apple.api.term.bean;

import com.benope.apple.domain.term.bean.Term;
import com.benope.apple.domain.term.bean.TermDetail;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class TermResponse {

    private TermCdResponse termCd;
    private String termVersion;
    private String termContent;

    public static TermResponse fromEntity(Term term) {
        TermDetail termDetail = term.getTermDetails().stream().findFirst().orElse(null);

        return TermResponse.builder()
                .termCd(DomainObjectUtil.nullSafeFunction(term.getTermCd(), TermCdResponse::fromEntity))
                .termVersion(DomainObjectUtil.nullSafeFunction(termDetail, TermDetail::getTermVersion))
                .termContent(DomainObjectUtil.nullSafeFunction(termDetail, TermDetail::getTermContent))
                .build();
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class TermCdResponse {

        private String code;
        private String desc;
        private boolean isMandatory;

        public static TermCdResponse fromEntity(Term.TermCd entity) {
            return TermCdResponse.builder()
                    .code(entity.getCode())
                    .desc(entity.getDesc())
                    .isMandatory(entity.isMandatory())
                    .build();
        }

    }

}
