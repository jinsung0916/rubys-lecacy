
package com.benope.apple.api.comment.controller;

import com.benope.apple.api.comment.bean.FoodCommentRequest;
import com.benope.apple.api.comment.bean.FoodCommentResponse;
import com.benope.apple.api.comment.bean.FoodCommentSearchRequest;
import com.benope.apple.api.comment.service.CommentScoreViewService;
import com.benope.apple.api.comment.service.CommentService;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.CommentScoreView;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.groups.Default;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class FoodCommentController {

    private final CommentScoreViewService commentScoreViewService;
    private final CommentService commentService;

    @PostMapping(value = "/get.food.comment")
    public ApiResponse getList(
            @RequestBody @Valid FoodCommentSearchRequest input
    ) {
        Page<CommentScoreView> page = commentScoreViewService.getList(input.toEntity(), input.toPageable());

        List<FoodCommentResponse> result = page.stream()
                .map(FoodCommentResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

    @PostMapping(value = "/reg.food.comment")
    public ApiResponse regist(
            @RequestBody @Validated({Default.class, Create.class}) FoodCommentRequest input
    ) {
        Comment comment = commentService.regist(input.toEntity());
        CommentScoreView view = commentScoreViewService.getOne(comment);
        return RstCode.SUCCESS.toApiResponse(FoodCommentResponse.fromEntity(view));
    }


    @PostMapping(value = "/mod.food.comment")
    public ApiResponse update(
            @RequestBody @Validated({Default.class, Update.class}) FoodCommentRequest input
    ) {
        Comment comment = commentService.update(input.toEntity());
        CommentScoreView view = commentScoreViewService.getOne(comment);
        return RstCode.SUCCESS.toApiResponse(FoodCommentResponse.fromEntity(view));
    }

    @PostMapping(value = "/del.food.comment")
    public ApiResponse delete(
            @RequestBody @Validated({Default.class, Delete.class}) FoodCommentRequest input
    ) {
        commentService.deleteById(input.getCommentNo());
        return RstCode.SUCCESS.toApiResponse();
    }

}
