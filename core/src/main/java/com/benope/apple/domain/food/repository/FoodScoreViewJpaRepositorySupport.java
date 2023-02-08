package com.benope.apple.domain.food.repository;

import com.benope.apple.config.domain.RowStatCd;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodScoreView;
import com.benope.apple.domain.food.bean.QFood;
import com.benope.apple.domain.food.bean.QFoodScoreView;
import com.benope.apple.domain.score.bean.QScore;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class FoodScoreViewJpaRepositorySupport extends QuerydslRepositorySupport {

    private static final QFood qFood = QFood.food;
    private static final QScore qScore = QScore.score;

    public FoodScoreViewJpaRepositorySupport(EntityManager em) {
        super(Food.class);
        setEntityManager(em);
    }

    public Page<FoodScoreView> getRandomList(Predicate predicate, Integer randNum, Pageable pageable) {
        Page<FoodScoreView> goeRandNum = goeRandNum(predicate, randNum, pageable);
        Page<FoodScoreView> ltRandNum = ltRandNum(predicate, randNum, pageable);

        List<FoodScoreView> results = Stream.concat(goeRandNum.stream(), ltRandNum.stream())
                .collect(Collectors.toUnmodifiableList());
        long totalCount = goeRandNum.getTotalElements() + ltRandNum.getTotalElements();
        return new PageImpl<>(results, pageable, totalCount);
    }

    private Page<FoodScoreView> goeRandNum(Predicate predicate, Integer randNum, Pageable pageable) {
        JPQLQuery<FoodScoreView> query = query(predicate)
                .where(qFood.randNum.goe(randNum));

        List<FoodScoreView> results = getQuerydsl().applyPagination(pageable, query).fetch();
        long totalCount = query.fetchCount();
        return new PageImpl<>(results, pageable, totalCount);
    }

    private Page<FoodScoreView> ltRandNum(Predicate predicate, Integer randNum, Pageable pageable) {
        JPQLQuery<FoodScoreView> query = query(predicate)
                .where(qFood.randNum.lt(randNum));

        List<FoodScoreView> results = getQuerydsl().applyPagination(pageable, query).fetch();
        long totalCount = query.fetchCount();
        return new PageImpl<>(results, pageable, totalCount);
    }

    private JPQLQuery<FoodScoreView> query(Predicate predicate) {
        return getQuerydsl().createQuery()
                .select(new QFoodScoreView(qFood, qScore))
                .from(qFood)
                .leftJoin(qScore).on(ExpressionUtils.allOf(
                        qScore.foodNo.eq(qFood.foodNo),
                        qFood.rowStatCd.ne(RowStatCd.D)
                ))
                .where(predicate);
    }

}
