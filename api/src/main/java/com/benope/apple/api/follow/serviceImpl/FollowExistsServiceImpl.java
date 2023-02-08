package com.benope.apple.api.follow.serviceImpl;

import com.benope.apple.api.follow.service.FollowExistsService;
import com.benope.apple.domain.follow.bean.Follow;
import com.benope.apple.domain.follow.repository.FollowJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowExistsServiceImpl implements FollowExistsService {

    private final FollowJpaRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean isFollowExists(Follow follow) {
        Follow cond = Follow.builder()
                .mbNo(follow.getMbNo())
                .followMbNo(follow.getFollowMbNo())
                .build();

        Example<Follow> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.exists(example);
    }

}
