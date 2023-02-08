package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodSolrEntity;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

public class FoodSolrCriteria {

    public static Criteria eq(FoodSolrEntity entity) {
        Criteria criteria = new Criteria();

        if (!CollectionUtils.isEmpty(entity.getText())) {
            criteria = criteria.and("textStr").contains(entity.getText());
        }

        if (Objects.nonNull(entity.getCategoryNo())) {
            criteria = criteria.and("categoryNo").is(entity.getCategoryNo());
        }

        if (Objects.nonNull(entity.getSubCategoryNo())) {
            criteria = criteria.and("subCategoryNo").is(entity.getSubCategoryNo());
        }

        return criteria;
    }

}
