package com.benope.apple.api.alarm.serviceImpl;

import com.benope.apple.api.alarm.bean.NotifyCommand;
import com.benope.apple.api.alarm.service.AlarmService;
import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.alarm.bean.Alarm;
import com.benope.apple.domain.alarm.event.AlarmViewEvent;
import com.benope.apple.domain.alarm.repository.AlarmJpaRepository;
import com.benope.apple.domain.alarm.repository.AlarmSpecifications;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmJpaRepository repository;

    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void notify(NotifyCommand notifyCommand) {
        if (notifyCommand.isSelfNotified()) {
            return;
        }

        repository.save(notifyCommand.toEntity());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Alarm> getList(Alarm alarm, Pageable pageable) {
        Specification<Alarm> spec = AlarmSpecifications.toSpec(alarm)
                .and(AlarmSpecifications.toSpecRelatedEntityExists());

        return repository.findAll(spec, pageable)
                .map(this::publishAlarmViewEvent);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Alarm> getById(Long alarmNo) {
        return repository.findById(alarmNo);
    }


    private Alarm publishAlarmViewEvent(Alarm alarm) {
        applicationEventPublisher.publishEvent(new AlarmViewEvent(alarm));
        return alarm;
    }

    @Transactional(readOnly = true)
    @Override
    public Long getCount(Alarm alarm) {
        Example<Alarm> example = Example.of(alarm, ExampleMatcher.matchingAll().withIgnoreNullValues());

        return repository.count(example);
    }

    @Override
    public Alarm update(Alarm alarm) {
        Alarm entity = repository.findById(alarm.getAlarmNo())
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        modelMapper.map(alarm, entity);
        return entity;
    }

    @Override
    public void deleteById(Long alarmNo) {
        Alarm entity = repository.findById(alarmNo)
                .orElseThrow(() -> new BusinessException(RstCode.NOT_FOUND));

        repository.delete(entity);
    }

}
