package com.benope.apple.api.comment.serviceImpl;

import com.benope.apple.api.comment.service.CommentService;
import com.benope.apple.api.comment.service.CountCommentService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.comment.bean.Comment;
import com.benope.apple.domain.comment.bean.CommunityComment;
import com.benope.apple.domain.comment.bean.FeedComment;
import com.benope.apple.domain.comment.event.CommunityCommentDeleteEvent;
import com.benope.apple.domain.comment.event.FeedCommentDeleteEvent;
import com.benope.apple.domain.comment.repository.CommentJpaRepository;
import com.benope.apple.utils.EntityManagerWrapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Primary
@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentJpaRepository commentRepository;
    private final EntityManagerWrapper em;

    private final CountCommentService countCommentService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public Page<Comment> getList(Comment comment, Pageable pageable) {
        Example<Comment> example = Example.of(comment, ExampleMatcher.matchingAll().withIgnoreNullValues());
        return commentRepository.findAll(example, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Comment> getById(Long commentNo) {
        return commentRepository.findById(commentNo);
    }

    @Override
    public Comment regist(Comment comment) {
        Comment entity = commentRepository.save(comment);

        plusSubCommentCount(entity);

        return em.flushAndRefresh(entity);
    }

    private void plusSubCommentCount(Comment comment) {
        if (comment.isSubComment()) {
            countCommentService.plusSubCommentCnt(comment.getParentCommentNo());
        }
    }

    @Override
    public Comment update(Comment comment) {
        Comment entity = commentRepository.findById(comment.getCommentNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(comment, entity);
        return em.flushAndRefresh(entity);
    }

    @Override
    public Comment deleteById(Long commentNo) {
        Comment entity = commentRepository.findById(commentNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        commentRepository.delete(entity);

        if (entity instanceof FeedComment) {
            applicationEventPublisher.publishEvent(new FeedCommentDeleteEvent((FeedComment) entity));
        } else if (entity instanceof CommunityComment) {
            applicationEventPublisher.publishEvent(new CommunityCommentDeleteEvent((CommunityComment) entity));
        }

        minusSubCommentCount(entity);

        return entity;
    }

    private void minusSubCommentCount(Comment comment) {
        if (comment.isSubComment()) {
            countCommentService.minusSubCommentCnt(comment.getParentCommentNo());
        }
    }

}
