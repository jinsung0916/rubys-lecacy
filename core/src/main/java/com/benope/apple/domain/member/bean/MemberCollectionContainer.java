package com.benope.apple.domain.member.bean;

import com.benope.apple.domain.term.bean.Term;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Embeddable
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCollectionContainer {

    @ElementCollection
    @CollectionTable(
            name = "mb_auth",
            joinColumns = {
                    @JoinColumn(
                            name = "mb_no",
                            referencedColumnName = "mb_no",
                            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
                    )
            }
    )
    @OrderColumn(name = "mb_auth_ord", columnDefinition = "BIGINT")
    private List<MemberAuth> memberAuths;

    public List<MemberAuth> getMemberAuths() {
        return DomainObjectUtil.nullSafeStream(this.memberAuths)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    @ElementCollection
    @CollectionTable(
            name = "mb_alarm_agree",
            joinColumns = {
                    @JoinColumn(
                            name = "mb_no",
                            referencedColumnName = "mb_no",
                            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
                    )
            }
    )
    @OrderColumn(name = "mb_alarm_agree_ord", columnDefinition = "BIGINT")
    private List<MemberAlarmAgree> memberAlarmAgrees;

    public List<MemberAlarmAgree> getMemberAlarmAgrees() {
        return DomainObjectUtil.nullSafeStream(this.memberAlarmAgrees)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<MemberAlarmAgree> getMemberAlarmAgree(MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd) {
        return getMemberAlarmAgrees()
                .stream()
                .filter(it -> alarmAgreeCd.equals(it.getAlarmAgreeCd()))
                .findAny();
    }

    public void alarmAgree(MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd) {
        Optional<MemberAlarmAgree> optional = getMemberAlarmAgree(alarmAgreeCd);
        if (optional.isPresent()) {
            optional.get().setAgree();
        } else {
            this.memberAlarmAgrees.add(
                    MemberAlarmAgree.builder()
                            .alarmAgreeCd(alarmAgreeCd)
                            .build()
                            .setAgree()
            );
        }
    }

    public void alarmDisagree(MemberAlarmAgree.AlarmAgreeCd alarmAgreeCd) {
        Optional<MemberAlarmAgree> optional = getMemberAlarmAgree(alarmAgreeCd);
        if (optional.isPresent()) {
            optional.get().setDisagree();
        } else {
            this.memberAlarmAgrees.add(
                    MemberAlarmAgree.builder()
                            .alarmAgreeCd(alarmAgreeCd)
                            .build()
                            .setDisagree()
            );
        }
    }

    @ElementCollection
    @CollectionTable(
            name = "mb_term_agree",
            joinColumns = {
                    @JoinColumn(
                            name = "mb_no",
                            referencedColumnName = "mb_no",
                            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
                    )
            }
    )
    @OrderColumn(name = "mb_terms_agree_ord", columnDefinition = "BIGINT")
    private List<MemberTermAgree> memberTermAgrees;

    public List<MemberTermAgree> getMemberTermAgrees() {
        return DomainObjectUtil.nullSafeStream(this.memberTermAgrees)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<MemberTermAgree> getLatestTermAgrees() {
        return Arrays.stream(Term.TermCd.values())
                .map(this::mapLatestTermAgree)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    private MemberTermAgree mapLatestTermAgree(Term.TermCd termCd) {
        return getMemberTermAgrees()
                .stream()
                .filter(it -> termCd.equals(it.getTermCd()))
                .sorted()
                .findFirst()
                .orElse(null);
    }

    private Optional<MemberTermAgree> getMemberTermAgree(Term.TermCd termCd, String termVersion) {
        return getMemberTermAgrees()
                .stream()
                .filter(it -> termCd.equals(it.getTermCd()) && termVersion.equals(it.getTermVersion()))
                .findAny();
    }

    public void termAgree(Term.TermCd termCd, String termVersion) {
        Optional<MemberTermAgree> optional = getMemberTermAgree(termCd, termVersion);
        if (optional.isPresent()) {
            optional.get().setAgree();
        } else {
            this.memberTermAgrees.add(
                    MemberTermAgree.builder()
                            .termCd(termCd)
                            .termVersion(termVersion)
                            .build()
                            .setAgree()
            );
        }
    }

    public void termDisagree(Term.TermCd termCd, String termVersion) {
        Optional<MemberTermAgree> optional = getMemberTermAgree(termCd, termVersion);
        if (optional.isPresent()) {
            optional.get().setDisagree();
        } else {
            this.memberTermAgrees.add(
                    MemberTermAgree.builder()
                            .termCd(termCd)
                            .termVersion(termVersion)
                            .build()
                            .setDisagree()
            );
        }
    }

    protected void validate() {
        DomainObjectUtil.nullSafeStream(getMemberAuths())
                .forEach(MemberAuth::validate);

        DomainObjectUtil.nullSafeStream(getMemberAlarmAgrees())
                .forEach(MemberAlarmAgree::validate);

        DomainObjectUtil.nullSafeStream(getMemberTermAgrees())
                .forEach(MemberTermAgree::validate);
    }

}
