package com.benope.apple.domain.score.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.score.bean.Score;

public class ScoreEvent extends BenopeEvent<Score> {
    public ScoreEvent(Score source) {
        super(source);
    }
}
