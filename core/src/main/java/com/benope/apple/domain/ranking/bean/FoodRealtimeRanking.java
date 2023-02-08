package com.benope.apple.domain.ranking.bean;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

@Entity
@DiscriminatorValue("FOOD_REALTIME")
@SQLDelete(sql = "UPDATE ranking SET row_stat_cd = 'D' WHERE ranking_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRealtimeRanking extends Ranking implements Comparable<FoodRealtimeRanking> {

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
        return RankingTypeCd.FOOD_REALTIME;
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

    public double getRankingValue() {
        long viewCount = Objects.requireNonNullElse(foodRankingData.getDailyViewCount(), 0L);
        long scoreCount = Objects.requireNonNullElse(foodRankingData.getDailyScoreCount(), 0L);
        long commentCount = Objects.requireNonNullElse(foodRankingData.getDailyCommentCount(), 0L);
        long tagCount = Objects.requireNonNullElse(foodRankingData.getDailyTagCount(), 0L);

        return viewCount + scoreCount * 3 + commentCount * 5 + tagCount * 10;
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

    @Override
    public int compareTo(FoodRealtimeRanking o) {
        return Comparator
                .comparingDouble(FoodRealtimeRanking::getRankingValue)
                .reversed()
                .thenComparing(FoodRealtimeRanking::getFoodNm)
                .compare(this, o);
    }
}
