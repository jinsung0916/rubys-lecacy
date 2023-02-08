package com.benope.apple.batch.batch007;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.food.bean.Food;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch007Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch007Processor implements ItemProcessor<Food, Food> {

    private final ModelMapper modelMapper;

    @Override
    public Food process(Food item) throws Exception {
        Food processed = Food.builder().build();
        modelMapper.map(item, processed);
        processed.refreshRandNum();
        return processed;
    }

}
