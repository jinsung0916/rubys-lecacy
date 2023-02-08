package com.benope.apple.api.food.bean;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodScoreView;
import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.benope.apple.domain.food.bean.UnitCd;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodResponse {

    private static final String DEFAULT_FOOD_VERSION = "0.1";

    private Long foodNo;
    private String foodNm;
    private String brand;

    private BigDecimal averageScore;
    private Long scoreCount;

    private BigDecimal score;

    private List<FoodDetail> foodDetails;

    public static FoodResponse fromEntity(Food entity) {
        return FoodResponse.builder()
                .foodNo(entity.getFoodNo())
                .foodNm(entity.getFoodNm())
                .brand(entity.getBrand())
                .averageScore(entity.getAverageScore())
                .scoreCount(entity.getScoreCount())
                .foodDetails(
                        List.of(
                                FoodDetail.builder()
                                        .foodNo(entity.getFoodNo())
                                        .foodVersion(DEFAULT_FOOD_VERSION)
                                        .carbohydrate(entity.getCarbohydrate())
                                        .carbohydrateUnitCd(Nutrients.CARBOHYDRATE.getUnit())
                                        .carbohydrateRatio(Nutrients.CARBOHYDRATE.toRatioString(entity.getCarbohydrate()))
                                        .sugars(entity.getSugars())
                                        .sugarsUnitCd(Nutrients.SUGARS.getUnit())
                                        .sugarsRatio(Nutrients.SUGARS.toRatioString(entity.getSugars()))
                                        .protein(entity.getProtein())
                                        .proteinUnitCd(Nutrients.PROTEIN.getUnit())
                                        .proteinRatio(Nutrients.PROTEIN.toRatioString(entity.getProtein()))
                                        .fat(entity.getFat())
                                        .fatUnitCd(Nutrients.FAT.getUnit())
                                        .fatRatio(Nutrients.FAT.toRatioString(entity.getFat()))
                                        .saturatedFat(entity.getSaturatedFat())
                                        .saturatedFatUnitCd(Nutrients.SATURATED_FAT.getUnit())
                                        .saturatedFatRatio(Nutrients.SATURATED_FAT.toRatioString(entity.getSaturatedFat()))
                                        .transFat(entity.getTransFat())
                                        .transFatUnitCd(Nutrients.TRANS_FAT.getUnit())
                                        .nateulyum(entity.getNateulyum())
                                        .nateulyumUnitCd(Nutrients.NATEULYUM.getUnit())
                                        .nateulyumRatio(Nutrients.NATEULYUM.toRatioString(entity.getNateulyum()))
                                        .cholesterol(entity.getCholesterol())
                                        .cholesterolUnitCd(Nutrients.CHOLESTEROL.getUnit())
                                        .cholesterolRatio(Nutrients.CHOLESTEROL.toRatioString(entity.getCholesterol()))
                                        .calories(entity.getCalories())
                                        .caloriesUnitCd(Nutrients.CALORIES.getUnit())
                                        .dietaryFiber(entity.getDietaryFiber())
                                        .dietaryFiberRatio(Nutrients.DIETARY_FIBER.toRatioString(entity.getDietaryFiber()))
                                        .dietaryFiberUnitCd(Nutrients.DIETARY_FIBER.getUnit())
                                        .allulose(entity.getAllulose())
                                        .alluloseUnitCd(Nutrients.ALLULOSE.getUnit())
                                        .erythritol(entity.getErythritol())
                                        .erythritolUnitCd(Nutrients.ERYTHRITOL.getUnit())
                                        .sugarAlcohol(entity.getSugarAlcohol())
                                        .sugarAlcoholUnitCd(Nutrients.SUGAR_ALCOHOL.getUnit())
                                        .weight(entity.getWeight())
                                        .weightUnitCd(DomainObjectUtil.nullSafeFunction(entity.getWeightUnitCd(), UnitCd::getDesc))
                                        .perServing(entity.getPerServing())
                                        .perServingUnitCd(DomainObjectUtil.nullSafeFunction(entity.getPerServingUnitCd(), UnitCd::getDesc))
                                        .nutrientStandards(entity.getNutrientStandards())
                                        .nutrientStandardsUnitCd(DomainObjectUtil.nullSafeFunction(entity.getNutrientStandardsUnitCd(), UnitCd::getDesc))
                                        .frontImgUrl(entity.getFrontImgUrl())
                                        .build()
                        )
                )
                .build();
    }

    public static FoodResponse fromEntity(FoodScoreView view) {
        FoodResponse response = fromEntity(view.getFood());
        response.score = DomainObjectUtil.nullSafeFunction(view.getScore(), Score::getScoreValue);
        return response;
    }

    public static FoodResponse fromEntity(FoodSolrEntity entity) {
        return FoodResponse.builder()
                .foodNo(entity.getId())
                .foodNm(entity.getFoodNm())
                .brand(entity.getBrand())
                .averageScore(nullSafeValueOf(entity.getAverageScore()))
                .scoreCount(entity.getScoreCount())
                .foodDetails(
                        List.of(
                                FoodDetail.builder()
                                        .foodNo(entity.getId())
                                        .foodVersion(DEFAULT_FOOD_VERSION)
                                        .carbohydrate(nullSafeValueOf(entity.getCarbohydrate()))
                                        .carbohydrateUnitCd(Nutrients.CARBOHYDRATE.getUnit())
                                        .carbohydrateRatio(Nutrients.CARBOHYDRATE.toRatioString(nullSafeValueOf(entity.getCarbohydrate())))
                                        .sugars(nullSafeValueOf(entity.getSugars()))
                                        .sugarsUnitCd(Nutrients.SUGARS.getUnit())
                                        .sugarsRatio(Nutrients.SUGARS.toRatioString(nullSafeValueOf(entity.getSugars())))
                                        .protein(nullSafeValueOf(entity.getProtein()))
                                        .proteinUnitCd(Nutrients.PROTEIN.getUnit())
                                        .proteinRatio(Nutrients.PROTEIN.toRatioString(nullSafeValueOf(entity.getProtein())))
                                        .fat(nullSafeValueOf(entity.getFat()))
                                        .fatUnitCd(Nutrients.FAT.getUnit())
                                        .fatRatio(Nutrients.FAT.toRatioString(nullSafeValueOf(entity.getFat())))
                                        .saturatedFat(nullSafeValueOf(entity.getSaturatedFat()))
                                        .saturatedFatUnitCd(Nutrients.SATURATED_FAT.getUnit())
                                        .saturatedFatRatio(Nutrients.SATURATED_FAT.toRatioString(nullSafeValueOf(entity.getSaturatedFat())))
                                        .transFat(nullSafeValueOf(entity.getTransFat()))
                                        .transFatUnitCd(Nutrients.TRANS_FAT.getUnit())
                                        .nateulyum(nullSafeValueOf(entity.getNateulyum()))
                                        .nateulyumUnitCd(Nutrients.NATEULYUM.getUnit())
                                        .nateulyumRatio(Nutrients.NATEULYUM.toRatioString(nullSafeValueOf(entity.getNateulyum())))
                                        .cholesterol(nullSafeValueOf(entity.getCholesterol()))
                                        .cholesterolUnitCd(Nutrients.CHOLESTEROL.getUnit())
                                        .cholesterolRatio(Nutrients.CHOLESTEROL.toRatioString(nullSafeValueOf(entity.getCholesterol())))
                                        .calories(nullSafeValueOf(entity.getCalories()))
                                        .caloriesUnitCd(Nutrients.CALORIES.getUnit())
                                        .dietaryFiber(nullSafeValueOf(entity.getDietaryFiber()))
                                        .dietaryFiberRatio(Nutrients.DIETARY_FIBER.toRatioString(nullSafeValueOf(entity.getDietaryFiber())))
                                        .dietaryFiberUnitCd(Nutrients.DIETARY_FIBER.getUnit())
                                        .allulose(nullSafeValueOf(entity.getAllulose()))
                                        .alluloseUnitCd(Nutrients.ALLULOSE.getUnit())
                                        .erythritol(nullSafeValueOf(entity.getErythritol()))
                                        .erythritolUnitCd(Nutrients.ERYTHRITOL.getUnit())
                                        .sugarAlcohol(nullSafeValueOf(entity.getSugarAlcohol()))
                                        .sugarAlcoholUnitCd(Nutrients.SUGAR_ALCOHOL.getUnit())
                                        .weight(nullSafeValueOf(entity.getWeight()))
                                        .weightUnitCd(DomainObjectUtil.nullSafeFunction(entity.getWeightUnitCd(), UnitCd::getDesc))
                                        .perServing(nullSafeValueOf(entity.getPerServing()))
                                        .perServingUnitCd(DomainObjectUtil.nullSafeFunction(entity.getPerServingUnitCd(), UnitCd::getDesc))
                                        .nutrientStandards(nullSafeValueOf(entity.getNutrientStandards()))
                                        .nutrientStandardsUnitCd(DomainObjectUtil.nullSafeFunction(entity.getNutrientStandardsUnitCd(), UnitCd::getDesc))
                                        .frontImgUrl(entity.getFrontImgUrl())
                                        .build()
                        )
                )
                .build();
    }

    private static BigDecimal nullSafeValueOf(Double value) {
        return Objects.nonNull(value)
                ? BigDecimal.valueOf(value)
                : null;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FoodDetail {
        private Long foodNo;
        private String foodVersion;
        private BigDecimal carbohydrate;
        private String carbohydrateUnitCd;
        private String carbohydrateRatio;
        private BigDecimal sugars;
        private String sugarsUnitCd;
        private String sugarsRatio;
        private BigDecimal protein;
        private String proteinUnitCd;
        private String proteinRatio;
        private BigDecimal fat;
        private String fatUnitCd;
        private String fatRatio;
        private BigDecimal saturatedFat;
        private String saturatedFatUnitCd;
        private String saturatedFatRatio;
        private BigDecimal transFat;
        private String transFatUnitCd;
        private BigDecimal nateulyum;
        private String nateulyumUnitCd;
        private String nateulyumRatio;
        private BigDecimal cholesterol;
        private String cholesterolUnitCd;
        private String cholesterolRatio;
        private BigDecimal calories;
        private String caloriesUnitCd;

        // 식이섬유
        private BigDecimal dietaryFiber;
        private String dietaryFiberUnitCd;
        private String dietaryFiberRatio;

        // 알룰로스
        private BigDecimal allulose;
        private String alluloseUnitCd;

        // 에리스리톨
        private BigDecimal erythritol;
        private String erythritolUnitCd;

        // 이외 당알코올
        private BigDecimal sugarAlcohol;
        private String sugarAlcoholUnitCd;

        private BigDecimal weight;
        private String weightUnitCd;
        private BigDecimal perServing;
        private String perServingUnitCd;
        private BigDecimal nutrientStandards;
        private String nutrientStandardsUnitCd;
        private String frontImgUrl;
    }
}
