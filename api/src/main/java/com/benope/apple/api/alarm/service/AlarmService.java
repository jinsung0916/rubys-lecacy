package com.benope.apple.api.alarm.service;

import com.benope.apple.api.alarm.bean.NotifyCommand;
import com.benope.apple.domain.alarm.bean.Alarm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Validated
public interface AlarmService {

    void notify(@Valid NotifyCommand notifyCommand);

    Page<Alarm> getList(@NotNull Alarm alarm, @NotNull Pageable pageable);

    Optional<Alarm> getById(@NotNull Long alarmNo);

    Long getCount(@NotNull Alarm alarm);

    Alarm update(@NotNull Alarm alarm);

    void deleteById(@NotNull Long alarmNo);

}
