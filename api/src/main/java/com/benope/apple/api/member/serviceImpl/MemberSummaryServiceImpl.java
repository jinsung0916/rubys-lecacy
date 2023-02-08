package com.benope.apple.api.member.serviceImpl;

import com.benope.apple.api.member.service.MemberSummaryService;
import com.benope.apple.domain.member.bean.MbSummaryCd;
import com.benope.apple.domain.member.bean.MemberSummary;
import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import com.benope.apple.domain.member.repository.MemberSummaryJpaRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSummaryServiceImpl implements MemberSummaryService {

    private final MemberSummaryJpaRepositorySupport repositorySupport;

    @Override
    public MemberSummaryComposite getMemberSummaries(Long mbNo) {
        List<MemberSummary> memberSummaries = Arrays.stream(MbSummaryCd.values())
                .map(it -> repositorySupport.findOne(it, mbNo, null, null))
                .collect(Collectors.toUnmodifiableList());

        return new MemberSummaryComposite(memberSummaries);
    }

}
