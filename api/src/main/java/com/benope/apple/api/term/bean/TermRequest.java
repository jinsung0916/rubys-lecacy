package com.benope.apple.api.term.bean;

import com.benope.apple.domain.term.bean.Term;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TermRequest {

    private Term.TermCd termCd;

    public Term toEntity() {
        return Term.builder()
                .termCd(termCd)
                .build();
    }
}
