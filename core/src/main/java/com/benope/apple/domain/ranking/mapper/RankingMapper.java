package com.benope.apple.domain.ranking.mapper;

import com.benope.apple.domain.ranking.bean.FoodRanking;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankingMapper {

    List<FoodRanking> generateFoodRanking(FoodRanking foodRanking);

}
