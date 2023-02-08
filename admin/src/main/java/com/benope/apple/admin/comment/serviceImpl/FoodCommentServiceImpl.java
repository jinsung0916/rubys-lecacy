package com.benope.apple.admin.comment.serviceImpl;

import com.benope.apple.admin.comment.service.FoodCommentService;
import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.domain.comment.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodCommentServiceImpl implements FoodCommentService {

    private final CommentJpaRepository repository;

    @Transactional(readOnly = true)
    @Override
    public Page<FoodComment> getList(FoodComment comment, Pageable pageable) {
        Example<FoodComment> example = Example.of(
                comment,
                ExampleMatcher.matchingAll().withIgnoreNullValues()
                        .withMatcher("food.brand", ExampleMatcher.GenericPropertyMatcher::contains)
        );

        return repository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FoodComment> getByIds(List<Long> ids) {
        return repository.findAllById(ids).stream()
                .map(it -> (FoodComment) it)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    @Override
    public long count(FoodComment foodComment) {
        Example<FoodComment> example = Example.of(foodComment, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.count(example);
    }
}
