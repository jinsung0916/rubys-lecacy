package com.benope.apple.domain.category.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.Audited;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.util.List;

@Audited
@Entity
@DiscriminatorValue("RANKING")
@SQLDelete(sql = "UPDATE category SET row_stat_cd = 'D' WHERE category_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingCategory extends Category {

    @ElementCollection
    @CollectionTable(
            name = "category_ranking_condition",
            joinColumns = {
                    @JoinColumn(
                            name = "category_no",
                            referencedColumnName = "category_no",
                            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
                    )
            }
    )
    @OrderColumn(name = "category_ranking_condition_ord", columnDefinition = "BIGINT")
    private List<RankingCondition> rankingConditions;

    @Override
    public CategoryTypeCd getCategoryTypeCd() {
        return CategoryTypeCd.RANKING;
    }

    @Override
    protected void validate() {
        Assert.notNull(rankingConditions);
    }
}
