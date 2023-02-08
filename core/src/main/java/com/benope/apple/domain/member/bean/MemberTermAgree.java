package com.benope.apple.domain.member.bean;

import com.benope.apple.domain.term.bean.Term;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;

@Embeddable
@Access(AccessType.FIELD)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberTermAgree implements Comparable<MemberTermAgree> {

    @Enumerated(EnumType.STRING)
    private Term.TermCd termCd;
    private String termVersion;
    private String termAgreeYn;
    private LocalDate termAgreeDt;

    public MemberTermAgree setAgree() {
        this.termAgreeYn = "Y";
        this.termAgreeDt = DateTimeUtil.getCurrentDate();
        return this;
    }

    public MemberTermAgree setDisagree() {
        this.termAgreeYn = "N";
        this.termAgreeDt = DateTimeUtil.getCurrentDate();
        return this;
    }

    public boolean isAgree() {
        return "Y".equalsIgnoreCase(getTermAgreeYn());
    }

    protected void validate() {
        Assert.notNull(termCd);
        Assert.notNull(termVersion);
        Assert.notNull(termAgreeYn);
    }

    @Override
    public int compareTo(MemberTermAgree o) {
        return Comparator.comparing(MemberTermAgree::getTermCd)
                .thenComparing(MemberTermAgree::getTermVersion)
                .reversed()
                .compare(this, o);
    }

}