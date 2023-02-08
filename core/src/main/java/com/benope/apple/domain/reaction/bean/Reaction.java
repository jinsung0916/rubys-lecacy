package com.benope.apple.domain.reaction.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.alarm.bean.AlarmTypeCd;
import com.benope.apple.domain.alarm.bean.Notifiable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "reaction")
@Where(clause = NOT_DELETED_CLAUSE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "reaction_target_cd")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Reaction extends AbstractDomain implements Notifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reactionNo;
    @Column(name = "mb_no")
    private Long mbNo;
    @Column(name = "object_no", insertable = false, updatable = false)
    private Long objectNo;
    @Enumerated(EnumType.STRING)
    private ReactionTypeCd reactionTypeCd;
    @Column(name = "reaction_target_cd", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ReactionTargetCd reactionTargetCd;

    public boolean isLike() {
        return ReactionTypeCd.LIKE.equals(this.reactionTypeCd);
    }

    @Override
    public AlarmTypeCd toAlarmTypeCd() {
        if (isLike()) {
            return AlarmTypeCd.LIKE;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toMessage() {
        if (isLike()) {
            return "%s님이 회원님의 게시글을 좋아합니다.";
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    protected void beforeCreate() {
        validate();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(mbNo);
        Assert.notNull(reactionTypeCd);
    }

}