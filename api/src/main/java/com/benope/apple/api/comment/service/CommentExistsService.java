package com.benope.apple.api.comment.service;

import com.benope.apple.domain.comment.bean.Comment;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CommentExistsService {

    boolean isFoodCommentExists(Comment comment);

}
