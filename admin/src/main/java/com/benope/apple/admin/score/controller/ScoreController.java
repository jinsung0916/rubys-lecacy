package com.benope.apple.admin.score.controller;

import com.benope.apple.admin.score.bean.ScoreRequest;
import com.benope.apple.admin.score.bean.ScoreResponse;
import com.benope.apple.admin.score.service.ScoreService;
import com.benope.apple.config.webMvc.AbstractRestController;
import com.benope.apple.config.webMvc.WebMvcConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/score", headers = WebMvcConfig.V2_HEADER)
@RequiredArgsConstructor
public class ScoreController extends AbstractRestController<ScoreRequest, ScoreResponse> {

    private final ScoreService scoreService;

    @Override
    protected Page<ScoreResponse> findAll(ScoreRequest input, Pageable pageable) {
        return scoreService.getList(input.toEntity(), pageable)
                .map(ScoreResponse::fromEntity);
    }

    @Override
    protected ScoreResponse findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<ScoreResponse> findByIds(List<Long> ids) {
        return scoreService.getByIds(ids)
                .stream()
                .map(ScoreResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected ScoreResponse create(ScoreRequest input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected ScoreResponse update(Long id, ScoreRequest input) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void deleteById(Long id) {
        throw new UnsupportedOperationException();
    }

}
