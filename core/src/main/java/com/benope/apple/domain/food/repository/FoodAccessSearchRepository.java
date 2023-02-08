package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodAccessDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FoodAccessSearchRepository extends ElasticsearchRepository<FoodAccessDocument, Long> {
}
