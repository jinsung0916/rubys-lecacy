package com.benope.apple.api.score.serviceImpl;

import com.benope.apple.api.score.service.ScoreExistsService;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.repository.ScoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreExistsServiceImpl implements ScoreExistsService {

    private final ScoreJpaRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean isExists(Score score) {
        Score cond = Score.builder()
                .mbNo(score.getMbNo())
                .foodNo(score.getFoodNo())
                .build();

        Example<Score> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.exists(example);
    }

}
