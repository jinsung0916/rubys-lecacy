package com.benope.apple.api.comment.serviceImpl;

import com.benope.apple.api.comment.service.CommentExistsService;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.FoodComment;
import com.benope.apple.domain.comment.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentExistsServiceImpl implements CommentExistsService {

    private final CommentJpaRepository repository;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    @Override
    public boolean isFoodCommentExists(Comment comment) {
        Comment cond = FoodComment.builder()
                .mbNo(comment.getMbNo())
                .foodNo(comment.getObjectNo())
                .build();

        Example<Comment> example = Example.of(cond, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return repository.exists(example);
    }

}
