package com.benope.apple.api.member.serviceImpl;

import com.benope.apple.api.member.service.MemberSearchService;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSolrEntity;
import com.benope.apple.domain.member.repository.MemberSolrCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class SolrMemberSearchService implements MemberSearchService {

    private final SolrTemplate solrTemplate;

    private final EntityManager em;

    @Override
    public Page<MemberSolrEntity> search(MemberSolrEntity entity, Pageable pageable) {
        return doSearch(entity, pageable)
                .map(this::retrieveMember);
    }

    public Page<MemberSolrEntity> doSearch(MemberSolrEntity entity, Pageable pageable) {
        Criteria criteria = MemberSolrCriteria.toCriteria(entity);
        return solrTemplate.queryForPage("member", new SimpleQuery(criteria).setPageRequest(pageable), MemberSolrEntity.class);
    }

    public MemberSolrEntity retrieveMember(MemberSolrEntity entity) {
        Member member = em.getReference(Member.class, entity.getId());
        entity.setMember(member);
        return entity;
    }

}
