package com.benope.apple.domain.food.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.food.bean.Food;

public class FoodUpdateEvent extends BenopeEvent<Food> {
    public FoodUpdateEvent(Food source) {
        super(source);
    }
}
