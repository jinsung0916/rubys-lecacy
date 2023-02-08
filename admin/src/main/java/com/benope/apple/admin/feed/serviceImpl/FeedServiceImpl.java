package com.benope.apple.admin.feed.serviceImpl;

import com.benope.apple.admin.feed.service.FeedService;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedSpecification;
import com.benope.apple.domain.feed.repository.FeedJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<Feed> getList(Feed feed, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Specification<Feed> spec = FeedSpecification.toSpec(feed)
                .and(FeedSpecification.toSpecCreatedAtBetween(startDate, endDate));

        return repository.findAll(spec, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Feed> getOne(Feed feed) {
        Example<Feed> example = Example.of(feed, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.findOne(example);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Feed> getByIds(List<Long> ids) {
        return repository.findAllById(ids);
    }

}
