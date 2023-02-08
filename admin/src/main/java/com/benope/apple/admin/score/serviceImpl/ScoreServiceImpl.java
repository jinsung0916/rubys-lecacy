package com.benope.apple.admin.score.serviceImpl;

import com.benope.apple.admin.score.service.ScoreService;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.repository.ScoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreJpaRepository repository;

    @Override
    public Page<Score> getList(Score score, Pageable pageable) {
        Example<Score> example = Example.of(
                score,
                ExampleMatcher.matchingAll().withIgnoreNullValues()
                        .withMatcher("food.brand", ExampleMatcher.GenericPropertyMatcher::contains)
        );

        return repository.findAll(example, pageable);
    }

    @Override
    public List<Score> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

}
