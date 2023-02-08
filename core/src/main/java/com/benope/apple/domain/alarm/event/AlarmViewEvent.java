package com.benope.apple.domain.alarm.event;

import com.benope.apple.config.domain.BenopeEvent;
import com.benope.apple.domain.alarm.bean.Alarm;

public class AlarmViewEvent extends BenopeEvent<Alarm> {
    public AlarmViewEvent(Alarm source) {
        super(source);
    }
}
