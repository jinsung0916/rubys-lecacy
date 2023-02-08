package com.benope.apple.domain.food.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.food.bean.Food;

public class FoodRegistEvent extends BenopeEvent<Food> {
    public FoodRegistEvent(Food source) {
        super(source);
    }
}
