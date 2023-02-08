package com.benope.apple.admin.food.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.ProductStatusCd;
import com.benope.apple.domain.food.bean.UnitCd;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodResponseV2 {

    private Long id;
    private String foodNm;
    private String brand;
    private Long foodCategoryNo;
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
    private UnitCd weightUnitCd;
    private BigDecimal perServing;
    private UnitCd perServingUnitCd;
    private BigDecimal nutrientStandards;
    private UnitCd nutrientStandardsUnitCd;
    private ProductStatusCd productStatusCd;
    private String storageType;
    private Long frontImageNo;
    private String writer;
    private Long tagCount;
    private BigDecimal totalScore;
    private Long scoreCount;
    private BigDecimal averageScore;
    private Long commentCount;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    private UploadImgResponseV2 frontImg;

    private Long categoryNo;
    private Long subCategoryNo;

    public static FoodResponseV2 fromEntity(Food food) {
        return FoodResponseV2.builder()
                .id(food.getFoodNo())
                .foodNm(food.getFoodNm())
                .brand(food.getBrand())
                .foodCategoryNo(food.getFoodCategoryNo())
                .manufacturer(food.getManufacturer())
                .carbohydrate(food.getCarbohydrate())
                .sugars(food.getSugars())
                .sugarAlcohol(food.getSugarAlcohol())
                .dietaryFiber(food.getDietaryFiber())
                .allulose(food.getAllulose())
                .erythritol(food.getErythritol())
                .protein(food.getProtein())
                .fat(food.getFat())
                .saturatedFat(food.getSaturatedFat())
                .transFat(food.getTransFat())
                .nateulyum(food.getNateulyum())
                .cholesterol(food.getCholesterol())
                .calories(food.getCalories())
                .weight(food.getWeight())
                .weightUnitCd(food.getWeightUnitCd())
                .perServing(food.getPerServing())
                .perServingUnitCd(food.getPerServingUnitCd())
                .nutrientStandards(food.getNutrientStandards())
                .nutrientStandardsUnitCd(food.getNutrientStandardsUnitCd())
                .productStatusCd(food.getProductStatusCd())
                .storageType(food.getStorageType())
                .frontImageNo(food.getFrontImageNo())
                .writer(food.getWriter())
                .tagCount(food.getTagCount())
                .totalScore(food.getTotalScore())
                .scoreCount(food.getScoreCount())
                .averageScore(food.getAverageScore())
                .createdAt(food.getZonedCreateAt())
                .updatedAt(food.getZonedUpdatedAt())
                .frontImg(
                        UploadImgResponseV2.builder()
                                .imgNo(food.getFrontImageNo())
                                .src(food.getFrontImgUrl())
                                .build()
                )
                .categoryNo(food.getCategoryNo())
                .subCategoryNo(food.getSubCategoryNo())
                .build();
    }

    public FoodResponseV2 setCommentCount(long commentCount) {
        this.commentCount = commentCount;

        return this;
    }

}
