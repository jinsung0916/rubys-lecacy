package com.benope.apple.batch.batch002;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.benope.apple.domain.food.repository.FoodSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.benope.apple.batch.batch002.Batch002Config.BATCH_ID;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = BATCH_ID)
@RequiredArgsConstructor
public class Batch002Writer implements ItemWriter<Food> {

    private final FoodSolrRepository foodSolrRepository;

    @Override
    public void write(List<? extends Food> items) throws Exception {
        for (Food food : items) {
            FoodSolrEntity entity = FoodSolrEntity.fromEntity(food);
            foodSolrRepository.save(entity);
        }
    }

}
