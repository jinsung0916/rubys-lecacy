package com.benope.apple.domain.member.repository;

import com.benope.apple.domain.member.bean.MemberSolrEntity;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface MemberSolrRepository extends SolrCrudRepository<MemberSolrEntity, String> {
}
