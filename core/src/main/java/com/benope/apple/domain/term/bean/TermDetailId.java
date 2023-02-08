package com.benope.apple.domain.term.bean;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TermDetailId implements Serializable {

    private static final long serialVersionUID = -4719928979991663952L;

    @Enumerated(EnumType.STRING)
    private Term.TermCd termCd;
    private String termVersion;

}
