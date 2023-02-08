package com.benope.apple.domain.alarm.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.alarm.bean.Alarm;

public class AlarmRegistEvent extends BenopeEvent<Alarm> {
    public AlarmRegistEvent(Alarm source) {
        super(source);
    }
}
