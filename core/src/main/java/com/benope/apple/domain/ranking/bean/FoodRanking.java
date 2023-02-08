package com.benope.apple.domain.ranking.bean;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@DiscriminatorValue("FOOD")
@SQLDelete(sql = "UPDATE ranking SET row_stat_cd = 'D' WHERE ranking_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRanking extends Ranking {

    @Column(name = "object_no")
    private Long foodNo;

    @Convert(converter = FoodRankingDataConverter.class)
    @Column(name = "info", columnDefinition = "json")
    private FoodRankingData foodRankingData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "object_no",
            referencedColumnName = "food_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Food food;

    @Override
    public RankingTypeCd getRankingTypeCd() {
        return RankingTypeCd.FOOD;
    }

    @Override
    public Long getObjectNo() {
        return this.foodNo;
    }

    public String getFoodNm() {
        return DomainObjectUtil.nullSafeEntityFunction(this.food, Food::getFoodNm);
    }

    public String getBrand() {
        return DomainObjectUtil.nullSafeEntityFunction(this.food, Food::getBrand);
    }

    public String getFrontImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.food, Food::getFrontImgUrl);
    }

    @Override
    protected void beforeCreate() {
        super.beforeCreate();

        validate();
    }

    @Override
    protected void beforeUpdate() {
        super.beforeUpdate();

        validate();
    }

    private void validate() {
        Assert.notNull(foodNo);
    }

    /* Mapper params */

    public int getMinAverageScore() {
        return 4;
    }

    public int getMinScoreCount() {
        return 1;
    }

    public LocalDateTime getMaxDateTime() {
        LocalDate yesterday = getRankDate().minusDays(1);
        LocalTime time = LocalTime.of(15, 0);
        return LocalDateTime.of(yesterday, time);
    }

}
