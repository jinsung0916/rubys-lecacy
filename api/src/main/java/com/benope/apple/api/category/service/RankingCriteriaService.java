package com.benope.apple.api.category.service;

import com.benope.apple.domain.category.bean.RankingCategory;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface RankingCriteriaService {

    List<RankingCategory> getList(@NotNull RankingCategory rankingCriteria);

}
