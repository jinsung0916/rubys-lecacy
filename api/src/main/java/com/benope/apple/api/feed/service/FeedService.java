package com.benope.apple.api.feed.service;

import com.benope.apple.domain.feed.bean.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface FeedService {

    Page<Feed> getList(@NotNull Feed feed, @NotNull Pageable pageable);

    Optional<Feed> getById(@NotNull Long feedNo, boolean isViewCounted);

    @NotNull Feed regist(@NotNull Feed feed);

    @PostAuthorize("returnObject.name == authentication.name")
    @NotNull Feed update(@NotNull Feed feed);

    @PostAuthorize("returnObject.name == authentication.name")
    @NotNull Feed deleteById(@NotNull Long feedNo);

}
