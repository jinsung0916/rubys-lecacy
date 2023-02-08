package com.benope.apple.admin.feed.serviceImpl;

import com.benope.apple.admin.feed.service.FeedSearchService;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.bean.FeedSpecification;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.domain.member.bean.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class SolrFeedSearchService implements FeedSearchService {

    private final SolrTemplate solrTemplate;

    private final EntityManager em;

    @Override
    public Page<FeedSolrEntity> search(FeedSolrEntity entity, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return doSearch(entity, startDate, endDate, pageable)
                .map(this::retrieveRpstImg)
                .map(this::retrieveMember);
    }

    private Page<FeedSolrEntity> doSearch(FeedSolrEntity entity, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Criteria criteria = FeedSpecification
                .toCriteria(entity)
                .and(FeedSpecification.toCriteriaCreatedAtBetween(startDate, endDate));

        return solrTemplate.queryForPage("feed", new SimpleQuery(criteria).setPageRequest(pageable), FeedSolrEntity.class);
    }

    private FeedSolrEntity retrieveRpstImg(FeedSolrEntity entity) {
        UploadImg rpstImg = em.getReference(UploadImg.class, entity.getRpstImgNo());
        entity.setRpstImg(rpstImg);
        return entity;
    }

    private FeedSolrEntity retrieveMember(FeedSolrEntity entity) {
        Member member = em.getReference(Member.class, entity.getMbNo());
        entity.setMember(member);
        return entity;
    }

}
