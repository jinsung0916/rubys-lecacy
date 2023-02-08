package com.benope.apple.domain.member.bean;

import com.benope.apple.utils.DateTimeUtil;
import lombok.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAuth {

    @Enumerated(EnumType.STRING)
    private MbAuthCd mbAuthCd;
    private String identifier;
    private String secret;
    private LocalDate authRegDt;

    protected void validate() {
        Assert.notNull(mbAuthCd);
        Assert.notNull(identifier);
    }

    public enum MbAuthCd {
        GOOGLE, APPLE, NAVER, KAKAO
    }

    /* Mapper params */

    public LocalDate getBaseDate() {
        return DateTimeUtil.getCurrentDate().minusMonths(1);
    }

}
