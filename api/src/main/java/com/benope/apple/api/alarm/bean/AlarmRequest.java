package com.benope.apple.api.alarm.bean;

import com.benope.apple.domain.alarm.bean.Alarm;
import com.benope.apple.utils.SessionUtil;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    public Alarm toEntity() {
        return Alarm.builder()
                .toMbNo(SessionUtil.getSessionMbNo())
                .build();
    }

    public Alarm toUnreadEntity() {
        return Alarm.builder()
                .toMbNo(SessionUtil.getSessionMbNo())
                .readYn("N")
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo -1 , recordsPerPage, Sort.by(Sort.Order.asc("readYn"), Sort.Order.desc("alarmNo")));
    }

}
