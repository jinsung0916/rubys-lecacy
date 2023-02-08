package com.benope.apple.domain.alarm.bean;

public interface Notifiable {

    AlarmTypeCd toAlarmTypeCd();

    String toMessage();

}
