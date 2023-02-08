package com.benope.apple.api.follow.serviceImpl;

import com.benope.apple.api.follow.service.FollowExistsService;
import com.benope.apple.api.follow.service.FollowService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.follow.bean.Follow;
import com.benope.apple.domain.follow.repository.FollowJpaRepository;
import com.benope.apple.domain.follow.repository.FollowQuerydslPredicates;
import com.benope.apple.utils.EntityManagerWrapper;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowJpaRepository repository;
    private final EntityManagerWrapper em;

    private final FollowExistsService followExistsService;

    @Transactional(readOnly = true)
    @Override
    public Page<Follow> getList(Follow follow, Pageable pageable) {
        Predicate predicate = ExpressionUtils.allOf(
                FollowQuerydslPredicates.eq(follow),
                FollowQuerydslPredicates.followerExists(),
                FollowQuerydslPredicates.followeeExists()
        );

        return repository.findAll(predicate, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Follow> getOne(Follow follow) {
        Predicate predicate = ExpressionUtils.allOf(
                FollowQuerydslPredicates.eq(follow),
                FollowQuerydslPredicates.followerExists(),
                FollowQuerydslPredicates.followeeExists()
        );

        return repository.findOne(predicate);
    }

    @Override
    public Follow regist(Follow follow) {
        Follow entity = repository.save(follow);

        if (followExistsService.isFollowExists(follow)) {
            throw new BusinessException(RstCode.ALREADY_REGISTERED_MEMBER);
        }

        return em.flushAndRefresh(entity);
    }

    @Override
    public Follow deleteById(Long followNo) {
        Follow entity = repository.findById(followNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
        return entity;
    }

}
