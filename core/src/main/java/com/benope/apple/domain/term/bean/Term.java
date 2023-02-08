package com.benope.apple.domain.term.bean;

import com.benope.apple.config.webMvc.EnumDescribed;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Term {

    private TermCd termCd;
    private List<TermDetail> termDetails;

    @RequiredArgsConstructor
    public enum TermCd implements EnumDescribed {

        TERM01("서비스 이용약관", true),
        TERM02("개인정보 처리방침", true),
        TERM03("개인정보 제3자 정보제공 동의", true),
        TERM04("마케팅 정보 수신동의", false);

        private final String desc;
        private final boolean isMandatory;

        @Override
        public String getCode() {
            return this.name();
        }

        @Override
        public String getDesc() {
            return this.desc;
        }

        public boolean isMandatory() {
            return this.isMandatory;
        }
    }

}