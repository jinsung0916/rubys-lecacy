package com.benope.apple.api.score.controller;

import com.benope.apple.api.food.service.FoodService;
import com.benope.apple.api.score.bean.FoodScoreRequest;
import com.benope.apple.api.score.bean.FoodScoreResponse;
import com.benope.apple.api.score.bean.FoodScoreSearchRequest;
import com.benope.apple.api.score.bean.FoodScoreSummary;
import com.benope.apple.api.score.service.ScoreFollowViewService;
import com.benope.apple.api.score.service.ScoreService;
import com.benope.apple.config.auth.AuthorityConstants;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.score.bean.Score;
import com.benope.apple.domain.score.bean.ScoreFollowView;
import com.benope.apple.utils.PagingMetadata;
import com.benope.apple.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FoodScoreController {

    private final ScoreFollowViewService scoreFollowViewService;
    private final FoodService foodService;
    private final ScoreService scoreService;

    @PostMapping("/get.food.score")
    public ApiResponse getList(
            @RequestBody @Valid FoodScoreSearchRequest input
    ) {
        Page<ScoreFollowView> page = scoreFollowViewService.getList(input.toEntity(), input.toPageable());

        List<FoodScoreResponse> response = page
                .stream()
                .map(FoodScoreResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

    @PreAuthorize("hasAuthority('" + AuthorityConstants.Token.ACCESS + "')")
    @PostMapping("/get.following.food.score")
    public ApiResponse getFollowerList(
            @RequestBody @Valid FoodScoreSearchRequest input
    ) {
        Page<ScoreFollowView> page = scoreFollowViewService.getFollowerList(input.toEntity(), input.toPageable());

        List<FoodScoreResponse> response = page
                .stream()
                .map(FoodScoreResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(response, PagingMetadata.withPage(page));
    }

    @PostMapping("/get.food.score.summary")
    public ApiResponse getSummary(
            @RequestBody @Valid FoodScoreRequest input
    ) {
        FoodScoreSummary summary = FoodScoreSummary.builder()
                .food(getFood(input))
                .myScore(getMyScore(input))
                .build();

        return RstCode.SUCCESS.toApiResponse(summary);
    }

    private Food getFood(FoodScoreRequest input) {
        return foodService.getById(input.getFoodNo(), false)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));
    }

    private BigDecimal getMyScore(FoodScoreRequest input) {
        Long mbNo = SessionUtil.getSessionMbNo();
        if (Objects.isNull(mbNo)) {
            return null;
        }

        return scoreService.getOne(
                        Score.builder()
                                .mbNo(mbNo)
                                .foodNo(input.getFoodNo())
                                .build()
                )
                .map(Score::getScoreValue)
                .orElse(null);
    }

    @PostMapping("/score.food")
    public ApiResponse score(
            @RequestBody @Validated({Default.class, Create.class}) FoodScoreRequest input
    ) {
        Score score = scoreService.score(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FoodScoreResponse.fromEntity(new ScoreFollowView(score, null)));
    }

    @PostMapping("/unscore.food")
    public ApiResponse unScore(
            @RequestBody @Valid FoodScoreRequest input
    ) {
        Score score = scoreService.unScore(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FoodScoreResponse.fromEntity(new ScoreFollowView(score, null)));
    }

}
