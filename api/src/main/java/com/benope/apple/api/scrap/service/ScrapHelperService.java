package com.benope.apple.api.scrap.service;

import com.benope.apple.domain.scrap.bean.Scrap;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ScrapHelperService {

    boolean checkScrap(@NotNull Long mbNo, @NotNull Long feedNo);

    void unScrap(@NotNull Long mbNo, @NotNull Long feedNo);

    void updateMultipleScrap(@NotNull List<Scrap> scraps);

}
