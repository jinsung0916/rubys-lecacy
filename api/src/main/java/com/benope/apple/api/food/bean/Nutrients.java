package com.benope.apple.api.food.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum Nutrients {

    CARBOHYDRATE(324, "g"),
    SUGARS(100, "g"),
    FAT(54, "g"),
    SATURATED_FAT(15, "g"),
    TRANS_FAT(null, "g"),
    PROTEIN(55, "g"),
    CHOLESTEROL(300, "mg"),
    NATEULYUM(2000, "mg"),
    CALORIES(null, "kcal"),
    DIETARY_FIBER(25, "g"),
    ALLULOSE(null, "g"),
    ERYTHRITOL(null, "g"),
    SUGAR_ALCOHOL(null, "g");

    private final Integer standard;
    private final String unit;

    public String toRatioString(BigDecimal value) {
        if (Objects.isNull(value)) {
            return null;
        }

        BigDecimal standard = BigDecimal.valueOf(this.standard);
        BigDecimal percentageConstant = BigDecimal.valueOf(100);
        BigDecimal ratio = value.multiply(percentageConstant).divide(standard, RoundingMode.HALF_UP);
        return ratio.setScale(0, RoundingMode.HALF_UP).toBigInteger() + "%";
    }

}
