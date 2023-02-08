package com.benope.apple.api.member.service;

import com.benope.apple.domain.member.bean.MemberSolrEntity;
import io.sentry.spring.tracing.SentrySpan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@SentrySpan
public interface MemberSearchService {

    Page<MemberSolrEntity> search(@NotNull MemberSolrEntity entity, @NotNull Pageable pageable);

}
