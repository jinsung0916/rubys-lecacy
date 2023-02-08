package com.benope.apple.api.comment.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface CountCommentService {

    void plusSubCommentCnt(@NotNull Long commentNo);

    void minusSubCommentCnt(@NotNull Long commentNo);

}
