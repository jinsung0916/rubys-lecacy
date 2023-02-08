package com.benope.apple.domain.food.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.food.bean.Food;

public class FoodViewEvent extends BenopeEvent<Food> {
    public FoodViewEvent(Food source) {
        super(source);
    }
}
