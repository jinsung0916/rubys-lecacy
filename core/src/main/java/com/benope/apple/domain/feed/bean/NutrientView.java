package com.benope.apple.domain.feed.bean;

import java.math.BigDecimal;

public interface NutrientView {

    BigDecimal getTotalCalories();

    BigDecimal getTotalFat();

    BigDecimal getTotalSaturatedFat();

    BigDecimal getTotalCarbohydrate();

    BigDecimal getTotalProtein();

}
