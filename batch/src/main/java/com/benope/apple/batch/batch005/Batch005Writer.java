package com.benope.apple.batch.batch005;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSolrEntity;
import com.benope.apple.domain.member.repository.MemberSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.benope.apple.batch.batch005.Batch005Config.BATCH_ID;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = BATCH_ID)
@RequiredArgsConstructor
public class Batch005Writer implements ItemWriter<Member> {

    private final MemberSolrRepository memberSolrRepository;

    @Override
    public void write(List<? extends Member> items) throws Exception {
        for (Member member : items) {
            MemberSolrEntity entity = MemberSolrEntity.fromEntity(member);
            memberSolrRepository.save(entity);
        }
    }

}
