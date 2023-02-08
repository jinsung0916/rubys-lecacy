package com.benope.apple.api.feed.serviceImpl;

import com.benope.apple.api.feed.service.FeedService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.event.FeedDeleteEvent;
import com.benope.apple.domain.feed.event.FeedViewEvent;
import com.benope.apple.domain.feed.repository.FeedJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FeedServiceImpl implements FeedService {

    private final FeedJpaRepository repository;
    private final EntityManagerWrapper em;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Feed> getList(Feed feed, Pageable pageable) {
        Example<Feed> example = Example.of(feed, ExampleMatcher.matchingAll().withIgnoreNullValues());

        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Feed> getById(Long feedNo, boolean isViewCounted) {
        Optional<Feed> optional = repository.findById(feedNo);

        if (optional.isPresent() && isViewCounted) {
            applicationEventPublisher.publishEvent(new FeedViewEvent(optional.get()));
        }

        return optional;
    }

    @Override
    public Feed regist(Feed feed) {
        Feed entity = repository.save(feed);

        return em.flushAndRefresh(entity);
    }


    @Override
    public Feed update(Feed feed) {
        Feed entity = repository.findById(feed.getFeedNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(feed, entity);
        repository.saveAndFlush(entity);

        return em.flushAndRefresh(entity);
    }

    @Override
    public Feed deleteById(Long feedNo) {
        Feed entity = repository.findById(feedNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);

        applicationEventPublisher.publishEvent(new FeedDeleteEvent(entity));

        return entity;
    }

}
