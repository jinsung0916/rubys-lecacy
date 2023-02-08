package com.benope.apple.admin.food.event;

import com.benope.apple.admin.food.service.FoodService;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.benope.apple.domain.food.event.FoodDeleteEvent;
import com.benope.apple.domain.food.event.FoodRegistEvent;
import com.benope.apple.domain.food.event.FoodUpdateEvent;
import com.benope.apple.domain.food.repository.FoodSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class FoodEventListener {

    private final FoodService foodService;

    private final FoodSolrRepository foodSolrRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FoodRegistEvent e) {
        Food food = e.getSource();
        Food refreshed = getRefreshed(food);

        FoodSolrEntity entity = FoodSolrEntity.fromEntity(refreshed);
        foodSolrRepository.save(entity);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FoodUpdateEvent e) {
        Food food = e.getSource();
        Food refreshed = getRefreshed(food);

        FoodSolrEntity entity = FoodSolrEntity.fromEntity(refreshed);
        foodSolrRepository.save(entity);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FoodDeleteEvent e) {
        Food food = e.getSource();
        String id = String.valueOf(food.getFoodNo());
        foodSolrRepository.deleteById(id);
    }

    private Food getRefreshed(Food food) {
        return foodService.getOne(
                        Food.builder()
                                .foodNo(food.getFoodNo())
                                .build()
                )
                .orElseThrow();
    }

}
