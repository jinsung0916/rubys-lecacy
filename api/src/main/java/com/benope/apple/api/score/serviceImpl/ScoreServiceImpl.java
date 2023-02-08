package com.benope.apple.api.score.serviceImpl;

import com.benope.apple.api.score.service.ScoreExistsService;
import com.benope.apple.api.score.service.ScoreService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.repository.ScoreJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private final ScoreJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ScoreExistsService scoreExistsService;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Optional<Score> getOne(Score score) {
        Example<Score> example = Example
                .of(score, ExampleMatcher.matchingAll().withIgnoreNullValues());

        return repository.findOne(example);
    }

    @Override
    public Score score(Score score) {
        Optional<Score> optional = getMemberScore(score);

        Score entity;
        if (optional.isEmpty()) {
            entity = repository.save(score);

            if (scoreExistsService.isExists(score)) {
                throw new BusinessException(RstCode.REGISTRATION_FAILED);
            }

        } else {
            entity = optional.get();
            modelMapper.map(score, entity);
            repository.saveAndFlush(entity);
        }

        return em.flushAndRefresh(entity);
    }

    @Override
    public Score unScore(Score score) {
        Score entity = getMemberScore(score)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
        return entity;
    }

    private Optional<Score> getMemberScore(Score score) {
        Score cond = Score.builder()
                .mbNo(score.getMbNo())
                .foodNo(score.getFoodNo())
                .build();

        return getOne(cond);
    }

}
