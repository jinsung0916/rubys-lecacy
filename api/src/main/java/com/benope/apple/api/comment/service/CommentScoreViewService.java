package com.benope.apple.api.comment.service;

import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.CommentScoreView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface CommentScoreViewService {

    Page<CommentScoreView> getList(@NotNull Comment comment, @NotNull Pageable pageable);

    CommentScoreView getOne(@NotNull Comment comment);

}
