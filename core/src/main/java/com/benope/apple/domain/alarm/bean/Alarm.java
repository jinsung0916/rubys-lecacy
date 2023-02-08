package com.benope.apple.domain.alarm.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.alarm.event.AlarmRegistEvent;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "alarm")
@Where(clause = NOT_DELETED_CLAUSE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "alarm_target_cd")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alarmNo;
    @Enumerated(EnumType.STRING)
    private AlarmTypeCd alarmTypeCd;
    @Enumerated(EnumType.STRING)
    @Column(
            name = "alarm_target_cd",
            insertable = false,
            updatable = false
    )
    private AlarmTargetCd alarmTargetCd;
    @Column(name = "from_mb_no")
    private Long fromMbNo;
    private Long toMbNo;
    @Column(name = "object_no", insertable = false, updatable = false)
    private Long objectNo;
    private String content;
    private String readYn;
    private String pushYn;
    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "from_mb_no",
            referencedColumnName = "mb_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member fromMember;

    public String getAlarmTargetDetailCd() {
        throw new UnsupportedOperationException();
    }

    public String getFromMemberNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.fromMember, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String getFromMemberProfileImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.fromMember, Member::getProfileImgUrl);
    }

    public String toAlarmContent() {
        return String.format(content, "<b>" + getFromMemberNickname() + "</b>");
    }

    public String toPushContent() {
        return String.format(content, getFromMemberNickname());
    }

    public boolean isRead() {
        return "Y".equalsIgnoreCase(getReadYn());
    }

    public void setRead() {
        this.readYn = "Y";
    }

    public void setPush() {
        this.pushYn = "Y";
    }

    @Override
    protected void beforeCreate() {
        validate();

        this.readYn = "N";
        this.pushYn = "N";

        registerEvent(new AlarmRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    protected void validate() {
        Assert.notNull(alarmTypeCd);
        Assert.notNull(fromMbNo);
        Assert.notNull(toMbNo);
        Assert.notNull(content);
    }
}