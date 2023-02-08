package com.benope.apple.admin.comment.service;

import com.benope.apple.domain.comment.bean.FoodComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface FoodCommentService {

    Page<FoodComment> getList(@NotNull FoodComment comment, @NotNull Pageable pageable);

    List<FoodComment> getByIds(@NotNull List<Long> ids);

    long count(@NotNull FoodComment foodComment);

}
