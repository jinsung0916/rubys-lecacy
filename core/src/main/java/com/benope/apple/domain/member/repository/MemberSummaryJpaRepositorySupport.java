package com.benope.apple.domain.member.repository;

import com.benope.apple.domain.member.bean.MbSummaryCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSummary;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@Repository
public class MemberSummaryJpaRepositorySupport extends QuerydslRepositorySupport {

    public MemberSummaryJpaRepositorySupport(EntityManager em) {
        super(Member.class);
        setEntityManager(em);
    }

    public MemberSummary findOne(MbSummaryCd mbSummaryCd, Long mbNo, LocalDate startDate, LocalDate endDate) {
        return mbSummaryCd.query(mbNo, startDate, endDate).fetchOne();
    }

}

