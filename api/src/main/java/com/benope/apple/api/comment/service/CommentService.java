package com.benope.apple.api.comment.service;

import com.benope.apple.domain.comment.bean.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface CommentService {

    Page<Comment> getList(@NotNull Comment comment, @NotNull Pageable pageable);

    Optional<Comment> getById(@NotNull Long commentNo);

    @NotNull Comment regist(@NotNull Comment comment);

    @PostAuthorize("returnObject.name == authentication.name")
    @NotNull Comment update(@NotNull Comment comment);

    @PostAuthorize("returnObject.name == authentication.name")
    @NotNull Comment deleteById(@NotNull Long commentNo);

}
