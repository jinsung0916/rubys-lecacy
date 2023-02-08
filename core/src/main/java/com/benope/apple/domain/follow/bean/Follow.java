package com.benope.apple.domain.follow.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.alarm.bean.AlarmTypeCd;
import com.benope.apple.domain.alarm.bean.Notifiable;
import com.benope.apple.domain.follow.event.FollowDeleteEvent;
import com.benope.apple.domain.follow.event.FollowRegistEvent;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.DateTimeUtil;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "follow")
@SQLDelete(sql = "UPDATE follow SET row_stat_cd = 'D' WHERE follow_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow extends AbstractDomain implements Notifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followNo;
    @Column(name = "mb_no")
    private Long mbNo;
    @Column(name = "follow_mb_no")
    private Long followMbNo;
    private LocalDate followDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "mb_no",
            referencedColumnName = "mb_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "follow_mb_no",
            referencedColumnName = "mb_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member following;

    public Long toFollowerMbNo() {
        return DomainObjectUtil.nullSafeEntityFunction(this.follower, Member::getMbNo);
    }

    public String toFollowerNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.follower, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String toFollowerProfileImageUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.follower, Member::getProfileImgUrl);
    }

    public Long toFollowingMbNo() {
        return DomainObjectUtil.nullSafeEntityFunction(this.following, Member::getMbNo);
    }

    public String toFollowingNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.following, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String toFollowingProfileImageUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.following, Member::getProfileImgUrl);
    }

    @Override
    public AlarmTypeCd toAlarmTypeCd() {
        return AlarmTypeCd.FOLLOW;
    }

    @Override
    public String toMessage() {
        return "%s님이 회원님을 팔로우하기 시작했습니다.";
    }

    @Override
    protected void beforeCreate() {
        validate();

        this.followDate = DateTimeUtil.getCurrentDate();

        registerEvent(new FollowRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new FollowDeleteEvent(this));
    }

    private void validate() {
        Assert.notNull(mbNo);
        Assert.notNull(followMbNo);
    }

}