package com.benope.apple.domain.comment.bean;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("FOOD")
@SQLDelete(sql = "UPDATE comment SET row_stat_cd = 'D' WHERE comment_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodComment extends Comment {

    @Column(name = "object_no")
    private Long foodNo;
    @Formula("CASE WHEN updated_at IS NULL THEN created_at ELSE updated_at END")
    private LocalDateTime createdAtOrUpdatedAt;

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
    public CommentTypeCd getCommentTypeCd() {
        return CommentTypeCd.FOOD;
    }

    @Override
    public Long getObjectNo() {
        return foodNo;
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

}
