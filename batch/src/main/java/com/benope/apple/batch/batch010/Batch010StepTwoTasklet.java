package com.benope.apple.batch.batch010;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.batch.DateTimeParams;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.CommentTypeCd;
import com.benope.apple.domain.comment.repository.CommentJpaRepository;
import com.benope.apple.domain.comment.repository.CommentQuerydslPredicates;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.FoodAccessDocument;
import com.benope.apple.domain.food.bean.FoodTag;
import com.benope.apple.domain.food.repository.FoodAccessSearchCriteria;
import com.benope.apple.domain.food.repository.FoodJpaRepository;
import com.benope.apple.domain.food.repository.FoodTagJpaRepository;
import com.benope.apple.domain.food.repository.FoodTagQuerydslPredicates;
import com.benope.apple.domain.ranking.bean.FoodRankingData;
import com.benope.apple.domain.ranking.bean.FoodRealtimeRanking;
import com.benope.apple.domain.ranking.repository.FoodRealtimeRankingJpaRepository;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.repository.ScoreJpaRepository;
import com.benope.apple.domain.score.repository.ScoreQuerydslPredicates;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = Batch010Config.BATCH_ID)
@RequiredArgsConstructor
public class Batch010StepTwoTasklet implements Tasklet {

    private static final int LIMIT = 10;

    private final DateTimeParams dateTimeParams;

    private final FoodJpaRepository foodJpaRepository;
    private final ScoreJpaRepository scoreJpaRepository;
    private final CommentJpaRepository commentJpaRepository;
    private final FoodTagJpaRepository foodTagJpaRepository;
    private final FoodRealtimeRankingJpaRepository foodRealtimeRankingJpaRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    private final EntityManager em;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        List<FoodRealtimeRanking> foodRealtimeRankings = foodJpaRepository.streamAll()
                .filter(this::frontImgNonNull)
                .filter(this::isNew)
                .map(this::toRankingAndDetach)
                .sorted()
                .limit(LIMIT)
                .collect(Collectors.toUnmodifiableList());

        for(int i = 0; i < foodRealtimeRankings.size(); i++) {
            foodRealtimeRankings.get(i).setRankNum((long) i + 1);
        }

        foodRealtimeRankingJpaRepository.saveAll(foodRealtimeRankings);

        return RepeatStatus.FINISHED;
    }

    private boolean frontImgNonNull(Food food) {
        return Objects.nonNull(food.getFrontImageNo());
    }

    private boolean isNew(Food food) {
        LocalDate yesterday = dateTimeParams.getDate().minusDays(1);

        FoodRealtimeRanking cond = FoodRealtimeRanking.builder()
                .foodNo(food.getFoodNo())
                .rankDate(yesterday)
                .build();

        Example<FoodRealtimeRanking> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return !foodRealtimeRankingJpaRepository.exists(example);
    }

    private FoodRealtimeRanking toRankingAndDetach(Food food) {
        LocalDate today = dateTimeParams.getDate();

        FoodRealtimeRanking ranking =  FoodRealtimeRanking.builder()
                .rankDate(today)
                .foodNo(food.getFoodNo())
                .foodRankingData(
                        FoodRankingData.builder()
                                .dailyViewCount(retrieveDailyViewCount(food))
                                .dailyScoreCount(retrieveDailyScoreCount(food))
                                .dailyCommentCount(retrieveDailyCommentCount(food))
                                .dailyTagCount(retrieveDailyTagCount(food))
                                .build()
                )
                .food(food)
                .build();

        em.detach(food);

        return ranking;
    }

    private long retrieveDailyViewCount(Food food) {
        FoodAccessDocument cond = FoodAccessDocument.builder()
                .foodNo(food.getFoodNo())
                .build();

        LocalDate yesterday = dateTimeParams.getDate().minusDays(1);

        Criteria criteria = FoodAccessSearchCriteria.eq(cond)
                .and(FoodAccessSearchCriteria.createdAtBetween(yesterday, yesterday));

        return elasticsearchOperations.count(new CriteriaQuery(criteria), FoodAccessDocument.class);
    }

    private long retrieveDailyScoreCount(Food food) {
        Score cond = Score.builder()
                .foodNo(food.getFoodNo())
                .build();

        LocalDate yesterday = dateTimeParams.getDate().minusDays(1);

        Predicate predicate = ExpressionUtils.allOf(
                ScoreQuerydslPredicates.eq(cond),
                ScoreQuerydslPredicates.createdAtOrUpdatedAtBetween(yesterday, yesterday)
        );

        return scoreJpaRepository.count(predicate);
    }

    private long retrieveDailyCommentCount(Food food) {
        Comment cond = Comment.builder()
                .commentTypeCd(CommentTypeCd.FOOD)
                .objectNo(food.getFoodNo())
                .build();

        LocalDate yesterday = dateTimeParams.getDate().minusDays(1);

        Predicate predicate = ExpressionUtils.allOf(
                CommentQuerydslPredicates.eq(cond),
                CommentQuerydslPredicates.createdAtBetween(yesterday, yesterday)
        );

        return commentJpaRepository.count(predicate);
    }

    private long retrieveDailyTagCount(Food food) {
        FoodTag cond = FoodTag.builder()
                .foodNo(food.getFoodNo())
                .build();

        LocalDate yesterday = dateTimeParams.getDate().minusDays(1);

        Predicate predicate = ExpressionUtils.allOf(
                FoodTagQuerydslPredicates.eq(cond),
                FoodTagQuerydslPredicates.createdAtBetween(yesterday, yesterday)
        );

        return foodTagJpaRepository.count(predicate);
    }

}
