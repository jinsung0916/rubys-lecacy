package com.benope.apple.api.food.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
public interface TagHelperService {

    void tag(@NotNull Long foodNo, @NotNull Long mbNo, @NotNull Long objectNo);

    void unTag(@NotNull Long mbNo, @NotNull Long objectNo);

}
