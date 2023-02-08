package com.benope.apple.domain.category.bean;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Audited
@Entity
@DiscriminatorValue("FOOD")
@SQLDelete(sql = "UPDATE category SET row_stat_cd = 'D' WHERE category_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodCategory extends Category {

    @Override
    public CategoryTypeCd getCategoryTypeCd() {
        return CategoryTypeCd.FOOD;
    }

}
