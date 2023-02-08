package com.benope.apple.api.feed.bean;

import com.benope.apple.api.food.bean.Nutrients;
import com.benope.apple.domain.feed.bean.NutrientView;
import com.benope.apple.domain.feed.bean.TaggedFood;
import com.benope.apple.domain.feed.bean.TaggedFoodComposite;
import com.benope.apple.domain.food.bean.UnitCd;
import lombok.*;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class NutrientViewResponse {

    private static final String UNKNOWN = "알 수 없음";

    private List<FoodSummary> foodSummaries;

    private String calories;
    private String fat;
    private String carbohydrate;
    private String protein;

    public static NutrientViewResponse fromEntity(NutrientView nutrientView) {
        return NutrientViewResponse.builder()
                .foodSummaries(toFoodSummaries(nutrientView))
                .calories(toCaloriesString(nutrientView))
                .fat(toFatString(nutrientView))
                .carbohydrate(toCarbohydrateString(nutrientView))
                .protein(toProteinString(nutrientView))
                .build();
    }

    private static List<FoodSummary> toFoodSummaries(NutrientView nutrientView) {
        if (nutrientView instanceof TaggedFoodComposite) {
            return doToFoodSummaries((TaggedFoodComposite) nutrientView);
        } else {
            return Collections.emptyList();
        }
    }

    private static List<FoodSummary> doToFoodSummaries(TaggedFoodComposite nutrientView) {
        List<FoodSummary> summaries = new ArrayList<>();

        for (TaggedFood taggedFood : nutrientView.getTags()) {
            try {
                summaries.add(FoodSummary.fromEntity(taggedFood));
            } catch (EntityNotFoundException e) {
                // bypass
            }
        }

        return summaries;
    }

    private static String toCaloriesString(NutrientView nutrientView) {
        return Objects.nonNull(nutrientView.getTotalCalories())
                ? nutrientView.getTotalCalories().stripTrailingZeros().toPlainString() + Nutrients.CALORIES.getUnit()
                : UNKNOWN;
    }

    private static String toFatString(NutrientView nutrientView) {
        StringBuilder sb = new StringBuilder();

        if (Objects.isNull(nutrientView.getTotalFat())) {
            return UNKNOWN;
        }

        sb.append(nutrientView.getTotalFat().stripTrailingZeros().toPlainString()).append(Nutrients.FAT.getUnit());

        if (Objects.nonNull(nutrientView.getTotalSaturatedFat())) {
            sb
                    .append("(포화지방 ")
                    .append(nutrientView.getTotalSaturatedFat().stripTrailingZeros().toPlainString())
                    .append(Nutrients.SATURATED_FAT.getUnit())
                    .append(")");
        }

        return sb.toString();
    }

    private static String toCarbohydrateString(NutrientView nutrientView) {
        return Objects.nonNull(nutrientView.getTotalCarbohydrate())
                ? nutrientView.getTotalCarbohydrate().stripTrailingZeros().toPlainString() + Nutrients.CARBOHYDRATE.getUnit()
                : UNKNOWN;
    }

    private static String toProteinString(NutrientView nutrientView) {
        return Objects.nonNull(nutrientView.getTotalProtein())
                ? nutrientView.getTotalProtein().stripTrailingZeros().toPlainString() + Nutrients.PROTEIN.getUnit()
                : UNKNOWN;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FoodSummary {
        private Long foodNo;
        private String foodNm;
        private String brand;
        private String perServing;
        private String calories;
        private String frontImgUrl;

        public static FoodSummary fromEntity(TaggedFood entity) {
            return FoodSummary.builder()
                    .foodNo(entity.getFoodNo())
                    .foodNm(entity.getFoodNm())
                    .brand(entity.getBrand())
                    .perServing(retrievePerServing(entity))
                    .calories(retrieveCalories(entity))
                    .frontImgUrl(entity.getFrontImgUrl())
                    .build();
        }

        private static String retrievePerServing(TaggedFood taggedFood) {
            Double userInput = taggedFood.getUserInput();
            UnitCd perServingUnitCd = taggedFood.getPerServingUnitCd();
            if (Objects.nonNull(userInput)) {
                return "1회 서빙량(" + userInput + perServingUnitCd.getDesc() + ")";
            } else {
                return UNKNOWN;
            }
        }

        private static String retrieveCalories(TaggedFood taggedFood) {
            BigDecimal calories = taggedFood.getTotalCalories();
            if (Objects.nonNull(calories)) {
                return calories.stripTrailingZeros().toPlainString() + Nutrients.CALORIES.getUnit();
            } else {
                return UNKNOWN;
            }
        }
    }
}
