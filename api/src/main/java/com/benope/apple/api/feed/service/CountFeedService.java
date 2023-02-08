package com.benope.apple.api.feed.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface CountFeedService {

    void plusViewCnt(@NotNull Long feedNo);

    void plusLikeCnt(@NotNull Long feedNo);
    void minusLikeCnt(@NotNull Long feedNo);

    void plusCommentCnt(@NotNull Long feedNo);
    void minusCommentCnt(@NotNull Long feedNo);

    void plusScrapCnt(@NotNull Long feedNo);
    void minusScrapCnt(@NotNull Long feedNo);

}
