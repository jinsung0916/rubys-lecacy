package com.benope.apple.batch.batch003;

import com.benope.apple.batch.BatchConstants;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSolrEntity;
import com.benope.apple.domain.feed.repository.FeedSolrRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.benope.apple.batch.batch003.Batch003Config.BATCH_ID;

@Component
@ConditionalOnProperty(value = BatchConstants.SPRING_BATCH_JOB_NAMES, havingValue = BATCH_ID)
@RequiredArgsConstructor
public class Batch003Writer implements ItemWriter<Feed> {

    private final FeedSolrRepository feedSolrRepository;

    @Override
    public void write(List<? extends Feed> items) throws Exception {
        for (Feed feed : items) {
            FeedSolrEntity entity = FeedSolrEntity.fromEntity(feed);
            feedSolrRepository.save(entity);
        }
    }

}
