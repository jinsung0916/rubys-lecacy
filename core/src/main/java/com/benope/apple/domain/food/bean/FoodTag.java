package com.benope.apple.domain.food.bean;

import com.benope.apple.config.domain.AbstractDomain;
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

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "food_tag")
@SQLDelete(sql = "UPDATE food_tag SET row_stat_cd = 'D' WHERE food_tag_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodTag extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodTagNo;
    @Column(name = "food_no")
    private Long foodNo;
    private Long mbNo;
    @Enumerated(EnumType.STRING)
    private FoodTagTargetCd foodTagTargetCd;
    private Long objectNo;

    @ManyToOne
    @JoinColumn(
            name = "food_no",
            referencedColumnName = "food_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    @NotFound(action = NotFoundAction.IGNORE)
    @Fetch(FetchMode.JOIN)
    private Food food;

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
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(foodNo);
        Assert.notNull(mbNo);
        Assert.notNull(objectNo);
    }
}
