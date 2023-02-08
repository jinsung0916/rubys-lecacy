package com.benope.apple.api.scrap.service;

import com.benope.apple.domain.scrap.bean.Scrap;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
public interface ScrapExistsService {

    boolean isExists(@NotNull Scrap scrap);

}
