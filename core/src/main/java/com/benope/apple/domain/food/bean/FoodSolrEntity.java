package com.benope.apple.domain.food.bean;

import com.benope.apple.config.domain.AbstractDocument;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Collection 삭제 후 재 식인 시 'pdouble' 에 sortMissingLast="true" 적용 후 Solr 재기동 요망.
 */
@SolrDocument(collection = "food")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodSolrEntity extends AbstractDocument {

    @Id
    private Long id;
    @Indexed(type = "string", copyTo = {"_text_", "_text_str_"})
    private String foodNm;
    @Indexed(type = "string", copyTo = {"_text_", "_text_str_"})
    private String brand;
    @Indexed(type = "plong")
    private Long foodCategoryNo;
    @Indexed(type = "string")
    private String manufacturer;
    @Indexed(type = "pdouble")
    private Double carbohydrate;
    @Indexed(type = "pdouble")
    private Double sugars;
    @Indexed(type = "pdouble")
    private Double sugarAlcohol;
    @Indexed(type = "pdouble")
    private Double dietaryFiber;
    @Indexed(type = "pdouble")
    private Double allulose;
    @Indexed(type = "pdouble")
    private Double erythritol;
    @Indexed(type = "pdouble")
    private Double protein;
    @Indexed(type = "pdouble")
    private Double fat;
    @Indexed(type = "pdouble")
    private Double saturatedFat;
    @Indexed(type = "pdouble")
    private Double transFat;
    @Indexed(type = "pdouble")
    private Double nateulyum;
    @Indexed(type = "pdouble")
    private Double cholesterol;
    @Indexed(type = "pdouble")
    private Double calories;
    @Indexed(type = "pdouble")
    private Double weight;
    @Indexed(type = "string")
    private UnitCd weightUnitCd;
    @Indexed(type = "pdouble")
    private Double perServing;
    @Indexed(type = "string")
    private UnitCd perServingUnitCd;
    @Indexed(type = "pdouble")
    private Double nutrientStandards;
    @Indexed(type = "string")
    private UnitCd nutrientStandardsUnitCd;
    @Indexed(type = "string")
    private ProductStatusCd productStatusCd;
    @Indexed(type = "string")
    private String storageType;
    @Indexed(type = "plong")
    private Long frontImageNo;
    @Indexed(type = "string")
    private String writer;

    @Indexed(type = "string", copyTo = {"_text_", "_text_str_"})
    private String brandAndFoodNm;

    @Indexed(type = "pdouble")
    private Double totalScore;
    @Indexed(type = "plong")
    private Long scoreCount;
    @Indexed(type = "pdouble")
    private Double averageScore;

    @Indexed(type = "plong")
    private Long categoryNo;
    @Indexed(type = "plong")
    private Long subCategoryNo;

    @Indexed(type = "boolean")
    private boolean isScoreCountAboveTen;

    @Transient
    private @Setter UploadImg frontImg;

    public String getFrontImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.frontImg, UploadImg::getImgFileUrl);
    }

    public static FoodSolrEntity fromEntity(Food food) {
        return FoodSolrEntity.builder()
                .id(food.getFoodNo())
                .foodNm(food.getFoodNm())
                .brand(food.getBrand())
                .foodCategoryNo(food.getFoodCategoryNo())
                .manufacturer(food.getManufacturer())
                .carbohydrate(DomainObjectUtil.nullSafeFunction(food.getCarbohydrate(), BigDecimal::doubleValue))
                .sugars(DomainObjectUtil.nullSafeFunction(food.getSugars(), BigDecimal::doubleValue))
                .sugarAlcohol(DomainObjectUtil.nullSafeFunction(food.getSugarAlcohol(), BigDecimal::doubleValue))
                .dietaryFiber(DomainObjectUtil.nullSafeFunction(food.getDietaryFiber(), BigDecimal::doubleValue))
                .allulose(DomainObjectUtil.nullSafeFunction(food.getAllulose(), BigDecimal::doubleValue))
                .erythritol(DomainObjectUtil.nullSafeFunction(food.getErythritol(), BigDecimal::doubleValue))
                .protein(DomainObjectUtil.nullSafeFunction(food.getProtein(), BigDecimal::doubleValue))
                .fat(DomainObjectUtil.nullSafeFunction(food.getFat(), BigDecimal::doubleValue))
                .saturatedFat(DomainObjectUtil.nullSafeFunction(food.getSaturatedFat(), BigDecimal::doubleValue))
                .transFat(DomainObjectUtil.nullSafeFunction(food.getTransFat(), BigDecimal::doubleValue))
                .nateulyum(DomainObjectUtil.nullSafeFunction(food.getNateulyum(), BigDecimal::doubleValue))
                .cholesterol(DomainObjectUtil.nullSafeFunction(food.getCholesterol(), BigDecimal::doubleValue))
                .calories(DomainObjectUtil.nullSafeFunction(food.getCalories(), BigDecimal::doubleValue))
                .weight(DomainObjectUtil.nullSafeFunction(food.getWeight(), BigDecimal::doubleValue))
                .weightUnitCd(food.getWeightUnitCd())
                .perServing(DomainObjectUtil.nullSafeFunction(food.getPerServing(), BigDecimal::doubleValue))
                .perServingUnitCd(food.getPerServingUnitCd())
                .nutrientStandards(DomainObjectUtil.nullSafeFunction(food.getNutrientStandards(), BigDecimal::doubleValue))
                .nutrientStandardsUnitCd(food.getNutrientStandardsUnitCd())
                .productStatusCd(food.getProductStatusCd())
                .storageType(food.getStorageType())
                .frontImageNo(food.getFrontImageNo())
                .writer(food.getWriter())
                .brandAndFoodNm(food.getBrand() + food.getFoodNm())
                .totalScore(DomainObjectUtil.nullSafeFunction(food.getTotalScore(), BigDecimal::doubleValue))
                .scoreCount(food.getScoreCount())
                .averageScore(DomainObjectUtil.nullSafeFunction(food.getAverageScore(), BigDecimal::doubleValue))
                .categoryNo(food.getCategoryNo())
                .subCategoryNo(food.getSubCategoryNo())
                .isScoreCountAboveTen(isScoreCountAboveTen(food))
                .build();
    }

    private static boolean isScoreCountAboveTen(Food food) {
        Long scoreCount = food.getScoreCount();
        return Objects.nonNull(scoreCount) && scoreCount >= 10;
    }
}
