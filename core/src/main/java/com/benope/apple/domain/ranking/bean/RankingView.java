package com.benope.apple.domain.ranking.bean;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.util.Objects;

@Getter
public class RankingView {

    private final Ranking now;
    private final Ranking prev;

    @QueryProjection
    public RankingView(Ranking now, Ranking prev) {
        this.now = now;
        this.prev = prev;
    }

    public Integer getStatus() {
        if (nowRankNumIsNull() || prevRankNumIsNull()) {
            return null;
        }

        return Math.toIntExact(prev.getRankNum() - now.getRankNum());
    }

    private boolean nowRankNumIsNull() {
        return Objects.isNull(now) || Objects.isNull(now.getRankNum());
    }

    private boolean prevRankNumIsNull() {
        return Objects.isNull(prev) || Objects.isNull(prev.getRankNum());
    }

}
