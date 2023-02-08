package com.benope.apple.domain.score.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.score.event.ScoreEvent;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.*;
import org.modelmapper.internal.util.Assert;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "score")
@Where(clause = NOT_DELETED_CLAUSE)
@SQLDelete(sql = "UPDATE score SET row_stat_cd = 'D' WHERE score_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Score extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreNo;
    @Column(name = "mb_no")
    private Long mbNo;
    @Column(name = "object_no")
    private Long foodNo;
    @Column(name = "score")
    private BigDecimal scoreValue;
    @Formula("CASE WHEN updated_at IS NULL THEN created_at ELSE updated_at END")
    private LocalDateTime createdAtOrUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "mb_no",
            referencedColumnName = "mb_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @ManyToOne
    @JoinColumn(
            name = "object_no",
            referencedColumnName = "food_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @NotFound(action = NotFoundAction.IGNORE)
    private Food food;

    public BigDecimal getScoreValue() {
        return Objects.nonNull(this.scoreValue) ? this.scoreValue.stripTrailingZeros() : null;
    }

    public String getMemberNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String getMemberProfileImageUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getProfileImgUrl);
    }

    public ExpertCd getMemberExpertCd() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getExpertCd);
    }

    public String getBrand() {
        return DomainObjectUtil.nullSafeEntityFunction(food, Food::getBrand);
    }

    public String getFoodNm() {
        return DomainObjectUtil.nullSafeEntityFunction(food, Food::getFoodNm);
    }

    public String getFrontImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(food, Food::getFrontImgUrl);
    }


    @Override
    protected void beforeCreate() {
        validate();

        registerEvent(new ScoreEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        validate();

        registerEvent(new ScoreEvent(this));
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new ScoreEvent(this));
    }

    private void validate() {
        Assert.notNull(mbNo);
        Assert.notNull(foodNo);
        Assert.notNull(scoreValue);

        if (isScoreBetweenZeroAndFive()) {
            throw new IllegalArgumentException();
        }

        if (!isScoreReminderZero()) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isScoreBetweenZeroAndFive() {
        return Objects.nonNull(scoreValue)
                && (BigDecimal.valueOf(0).compareTo(scoreValue) >= 0 || BigDecimal.valueOf(5).compareTo(scoreValue) < 0);
    }

    private boolean isScoreReminderZero() {
        return Objects.nonNull(scoreValue)
                && BigDecimal.ZERO.equals(scoreValue.remainder(BigDecimal.valueOf(0.5)).stripTrailingZeros());
    }

}
