package com.benope.apple.domain.food.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.category.bean.Category;
import com.benope.apple.domain.food.event.FoodDeleteEvent;
import com.benope.apple.domain.food.event.FoodRegistEvent;
import com.benope.apple.domain.food.event.FoodUpdateEvent;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Audited
@Entity
@Table(
        name = "food",
        indexes = @Index(name = "food__idx1", columnList = "food_category_no, rand_num")
)
@SQLDelete(sql = "UPDATE food SET row_stat_cd = 'D' WHERE food_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class Food extends AbstractDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    private static final int MAX_BOUND = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_no")
    private Long foodNo;
    @Column(name = "food_category_no")
    private Long foodCategoryNo;
    private String foodNm;
    private String brand;
    private String manufacturer;
    private BigDecimal carbohydrate;
    private BigDecimal sugars;
    private BigDecimal sugarAlcohol;
    private BigDecimal dietaryFiber;
    private BigDecimal allulose;
    private BigDecimal erythritol;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal saturatedFat;
    private BigDecimal transFat;
    private BigDecimal nateulyum;
    private BigDecimal cholesterol;
    private BigDecimal calories;
    private BigDecimal weight;
    @Enumerated(EnumType.STRING)
    private UnitCd weightUnitCd;
    private BigDecimal perServing;
    @Enumerated(EnumType.STRING)
    private UnitCd perServingUnitCd;
    private BigDecimal nutrientStandards;
    @Enumerated(EnumType.STRING)
    private UnitCd nutrientStandardsUnitCd;
    @Enumerated(EnumType.STRING)
    private ProductStatusCd productStatusCd;
    private String storageType;
    @Column(name = "front_image_no")
    private @Setter Long frontImageNo;
    private String writer;

    private Long tagCount;

    private BigDecimal totalScore;
    private Long scoreCount;

    @Column(name = "rand_num")
    private Integer randNum;

    @NotAudited
    @Formula("CASE WHEN score_count != 0 THEN total_score / score_count ELSE null END")
    private Long averageScore;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "front_image_no",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "food_category_no",
            referencedColumnName = "category_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Category category;

    public String getFrontImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.uploadImg, UploadImg::getImgFileUrl);
    }

    public Long getCategoryNo() {
        return DomainObjectUtil.nullSafeEntityFunction(this.category, Category::getParentCategoryNo);
    }

    public Long getSubCategoryNo() {
        return DomainObjectUtil.nullSafeEntityFunction(this.category, Category::getCategoryNo);
    }

    public String getCategoryNm() {
        return DomainObjectUtil.nullSafeEntityFunction(this.category, Category::getParentCategoryNm);
    }

    public String getSubCategoryNm() {
        return DomainObjectUtil.nullSafeEntityFunction(this.category, Category::getCategoryNm);
    }

    public BigDecimal getAverageScore() {
        if (Objects.requireNonNullElse(scoreCount, 0L) == 0) {
            return null;
        }

        return totalScore.divide(BigDecimal.valueOf(scoreCount), 1, RoundingMode.HALF_UP)
                .stripTrailingZeros();
    }

    public void refreshRandNum() {
        this.randNum = ThreadLocalRandom.current().nextInt(MAX_BOUND);
    }

    @Override
    protected void beforeCreate() {
        validate();

        this.tagCount = 0L;
        this.totalScore = BigDecimal.ZERO;
        this.scoreCount = 0L;
        refreshRandNum();

        registerEvent(new FoodRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        validate();

        registerEvent(new FoodUpdateEvent(this));
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new FoodDeleteEvent(this));
    }

    private void validate() {
        Assert.notNull(foodCategoryNo);
        Assert.notNull(foodNm);
        Assert.notNull(brand);
        Assert.notNull(weight);
        Assert.notNull(weightUnitCd);
        Assert.notNull(perServingUnitCd);
        Assert.notNull(nutrientStandards);
        Assert.notNull(nutrientStandardsUnitCd);
        Assert.notNull(writer);
    }

}