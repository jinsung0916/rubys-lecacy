package com.benope.apple.api.scrap.service;

import com.benope.apple.domain.scrap.bean.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface ScrapService {

    Page<Scrap> getList(@NotNull Scrap scrap, @NotNull Pageable pageable);

    Optional<Scrap> getOne(@NotNull Scrap scrap);

    @NotNull Scrap registScrap(@NotNull Scrap scrap);

    @NotNull Scrap registDirectory(@NotNull Scrap scrap);

    @NotNull Scrap updateScrap(@NotNull Scrap scrap);

    @NotNull Scrap updateDirectory(@NotNull Scrap scrap);

    void deleteScrapById(@NotNull Long scrapNo);

    int deleteDirectoryById(@NotNull Long directoryNo);

}
