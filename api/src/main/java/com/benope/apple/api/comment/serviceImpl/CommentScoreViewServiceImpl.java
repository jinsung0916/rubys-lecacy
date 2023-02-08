package com.benope.apple.api.comment.serviceImpl;

import com.benope.apple.api.comment.service.CommentScoreViewService;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.CommentScoreView;
import com.benope.apple.domain.comment.repository.CommentQuerydslPredicates;
import com.benope.apple.domain.comment.repository.CommentScoreViewJpaRepositorySupport;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentScoreViewServiceImpl implements CommentScoreViewService {

    private final CommentScoreViewJpaRepositorySupport repositorySupport;

    @Transactional(readOnly = true)
    @Override
    public Page<CommentScoreView> getList(Comment comment, Pageable pageable) {
        Predicate predicate = CommentQuerydslPredicates.eq(comment);
        return repositorySupport.findAll(predicate, pageable);
    }

    @Override
    public CommentScoreView getOne(Comment comment) {
        Predicate predicate = CommentQuerydslPredicates.eq(comment);
        return repositorySupport.findOne(predicate);
    }

}
