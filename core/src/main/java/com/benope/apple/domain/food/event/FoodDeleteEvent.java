package com.benope.apple.domain.food.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.food.bean.Food;

public class FoodDeleteEvent extends BenopeEvent<Food> {
    public FoodDeleteEvent(Food source) {
        super(source);
    }
}
