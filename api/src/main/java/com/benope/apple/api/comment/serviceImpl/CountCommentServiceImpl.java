package com.benope.apple.api.comment.serviceImpl;

import com.benope.apple.api.comment.service.CountCommentService;
import com.benope.apple.domain.comment.repository.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CountCommentServiceImpl implements CountCommentService {

    private final CommentJpaRepository repository;

    @Override
    public void plusSubCommentCnt(Long commentNo) {
        repository.plusSubCommentCnt(commentNo);
    }

    @Override
    public void minusSubCommentCnt(Long commentNo) {
        repository.minusSubCommentCnt(commentNo);
    }

}
