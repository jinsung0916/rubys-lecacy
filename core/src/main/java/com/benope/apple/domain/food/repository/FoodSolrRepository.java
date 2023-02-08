package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodSolrEntity;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface FoodSolrRepository extends SolrCrudRepository<FoodSolrEntity, String> {
}
