package com.benope.apple.domain.food.repository;

import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.domain.food.bean.QFoodTag;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class FoodTagJpaRepositorySupport extends QuerydslRepositorySupport {

    private final QFoodTag qFoodTag = QFoodTag.foodTag;

    public FoodTagJpaRepositorySupport(EntityManager em) {
        super(FoodTag.class);
        setEntityManager(em);
    }

    public long countDistinct(Predicate predicate) {
        return getQuerydsl().createQuery()
                .select(countDistinctOfMbNo())
                .from(qFoodTag)
                .where(predicate)
                .fetchOne();
    }

    private Expression<Long> countDistinctOfMbNo() {
        return qFoodTag.mbNo.countDistinct();
    }

}
