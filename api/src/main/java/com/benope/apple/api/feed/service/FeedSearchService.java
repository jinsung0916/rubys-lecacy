package com.benope.apple.api.feed.service;

import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import io.sentry.spring.tracing.SentrySpan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@SentrySpan
public interface FeedSearchService {

    Page<FeedSolrEntity> search(@NotNull FeedSolrEntity entity, @NotNull Pageable pageable);

}
