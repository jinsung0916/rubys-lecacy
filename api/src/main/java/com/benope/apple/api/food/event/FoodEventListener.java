package com.benope.apple.api.food.event;

import com.benope.apple.api.food.service.FoodService;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodAccessDocument;
import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.benope.apple.domain.food.event.FoodUpdateEvent;
import com.benope.apple.domain.food.event.FoodViewEvent;
import com.benope.apple.domain.food.repository.FoodAccessSearchRepository;
import com.benope.apple.domain.food.repository.FoodSolrRepository;
import com.benope.apple.utils.DateTimeUtil;
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

    private final FoodAccessSearchRepository foodAccessSearchRepository;
    private final FoodSolrRepository foodSolrRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FoodViewEvent e) {
        Food food = e.getSource();

        FoodAccessDocument document = FoodAccessDocument.builder()
                .foodNo(food.getFoodNo())
                .createdAt(DateTimeUtil.getCurrentDateTime())
                .build();

        foodAccessSearchRepository.save(document);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleAsyncEvent(FoodUpdateEvent e) {
        Food food = e.getSource();
        Food refreshed = foodService.getById(food.getFoodNo(), false).orElseThrow();

        FoodSolrEntity entity = FoodSolrEntity.fromEntity(refreshed);
        foodSolrRepository.save(entity);
    }

}
