package com.benope.apple.api.reaction.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface FeedReactionService {

    boolean isFeedLike(@NotNull Long mbNo, @NotNull Long feedNo);

    boolean toggleFeedLike(@NotNull Long mbNo, @NotNull Long feedNo);

}
