package com.benope.apple.api.food.service;

import com.benope.apple.domain.food.bean.FoodSolrEntity;
import io.sentry.spring.tracing.SentrySpan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@SentrySpan
public interface FoodSearchService {

    Page<FoodSolrEntity> search(@NotNull FoodSolrEntity entity, @NotNull Pageable pageRequest);

}
