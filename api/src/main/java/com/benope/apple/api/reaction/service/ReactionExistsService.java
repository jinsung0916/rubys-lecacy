package com.benope.apple.api.reaction.service;

import com.benope.apple.domain.reaction.bean.Reaction;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ReactionExistsService {

    boolean isExists(@NotNull Reaction reaction);

}
