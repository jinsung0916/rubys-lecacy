package com.benope.apple.domain.feed.repository;

import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface FeedSolrRepository extends SolrCrudRepository<FeedSolrEntity, String> {
}
