package com.benope.apple.domain.scrap.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.scrap.bean.Scrap;

public class ScrapRegistEvent extends BenopeEvent<Scrap> {
    public ScrapRegistEvent(Scrap source) {
        super(source);
    }
}
