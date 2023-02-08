package com.benope.apple.admin.member.serviceImpl;

import com.benope.apple.admin.member.service.MemberSummaryService;
import com.benope.apple.domain.member.bean.MbSummaryCd;
import com.benope.apple.domain.member.bean.MemberSummary;
import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import com.benope.apple.domain.member.repository.MemberSummaryJpaRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSummaryServiceImpl implements MemberSummaryService {

    private final MemberSummaryJpaRepositorySupport repositorySupport;

    @Transactional(readOnly = true)
    @Override
    public MemberSummaryComposite getMemberSummaries(Long mbNo, LocalDate startDate, LocalDate endDate) {
        List<MemberSummary> memberSummaries = Arrays.stream(MbSummaryCd.values())
                .map(it -> repositorySupport.findOne(it, mbNo, startDate, endDate))
                .collect(Collectors.toUnmodifiableList());

        return new MemberSummaryComposite(memberSummaries);
    }

}
