package com.benope.apple.api.comment.controller;

import com.benope.apple.api.comment.bean.FeedCommentRequest;
import com.benope.apple.api.comment.bean.FeedCommentResponse;
import com.benope.apple.api.comment.bean.FeedCommentSearchRequest;
import com.benope.apple.api.comment.service.CommentService;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.utils.PagingMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FeedCommentController {

    private final CommentService commentService;

    @PostMapping(value = "get.feed.comment")
    public ApiResponse getList(
            @RequestBody @Valid FeedCommentSearchRequest input
    ) {
        Page<Comment> page = commentService.getList(input.toEntity(), input.toPageable());

        List<FeedCommentResponse> result = page
                .stream()
                .map(FeedCommentResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());

        return RstCode.SUCCESS.toApiResponse(result, PagingMetadata.withPage(page));
    }

    @PostMapping(value = "reg.feed.comment")
    public ApiResponse regist(
            @RequestBody @Validated({Default.class, Create.class}) FeedCommentRequest input
    ) {
        Comment comment = commentService.regist(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FeedCommentResponse.fromEntity(comment));
    }


    @PostMapping(value = "mod.feed.comment")
    public ApiResponse update(
            @RequestBody @Validated({Default.class, Update.class}) FeedCommentRequest input
    ) {
        Comment comment = commentService.update(input.toEntity());
        return RstCode.SUCCESS.toApiResponse(FeedCommentResponse.fromEntity(comment));
    }

    @PostMapping(value = "del.feed.comment")
    public ApiResponse delete(
            @RequestBody @Validated({Default.class, Delete.class}) FeedCommentRequest input
    ) {
        commentService.deleteById(input.getCommentNo());
        return RstCode.SUCCESS.toApiResponse();
    }

}
