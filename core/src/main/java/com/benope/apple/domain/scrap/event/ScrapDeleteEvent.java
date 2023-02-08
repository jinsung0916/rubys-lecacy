package com.benope.apple.domain.scrap.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.scrap.bean.Scrap;

public class ScrapDeleteEvent extends BenopeEvent<Scrap> {
    public ScrapDeleteEvent(Scrap source) {
        super(source);
    }
}
