package com.benope.apple.domain.member.repository;

import com.benope.apple.domain.member.bean.MemberSolrEntity;
import org.springframework.data.solr.core.query.Criteria;

import java.util.Objects;

public class MemberSolrCriteria {

    // Solr

    public static Criteria toCriteria(MemberSolrEntity entity) {
        Criteria criteria = new Criteria();

        if (Objects.nonNull(entity.getNickname())) {
            criteria = criteria.and("nickname").contains(entity.getNickname());
        }

        return criteria;
    }

}
