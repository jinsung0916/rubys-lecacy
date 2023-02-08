package com.benope.apple.api.food.serviceImpl;

import com.benope.apple.api.food.service.FoodSearchService;
import com.benope.apple.domain.food.bean.FoodSolrEntity;
import com.benope.apple.domain.food.repository.FoodSolrCriteria;
import com.benope.apple.domain.image.bean.UploadImg;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SolrFoodSearchService implements FoodSearchService {

    private final SolrTemplate solrTemplate;

    private final EntityManager em;

    @Override
    public Page<FoodSolrEntity> search(FoodSolrEntity entity, Pageable pageable) {
        return doSearch(entity, pageable)
                .map(this::retrieveFrontImg);
    }

    private Page<FoodSolrEntity> doSearch(FoodSolrEntity entity, Pageable pageable) {
        Criteria criteria = FoodSolrCriteria.eq(entity);
        return solrTemplate.queryForPage("food", new SimpleQuery(criteria).setPageRequest(pageable), FoodSolrEntity.class);
    }

    private FoodSolrEntity retrieveFrontImg(FoodSolrEntity entity) {
        if (Objects.nonNull(entity.getFrontImageNo())) {
            UploadImg frontImg = em.getReference(UploadImg.class, entity.getFrontImageNo());
            entity.setFrontImg(frontImg);
        }

        return entity;
    }

}
