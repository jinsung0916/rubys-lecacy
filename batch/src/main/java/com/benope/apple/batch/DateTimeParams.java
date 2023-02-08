package com.benope.apple.batch;

import com.benope.apple.utils.DateTimeUtil;
import lombok.Data;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
@JobScope
@Data
public class DateTimeParams {

    @Value("#{jobParameters[dateTime]}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, fallbackPatterns = "yyyy-MM-dd'T'HH:mm:ss[xxx][xx][X]")
    private ZonedDateTime dateTime;

    public LocalDateTime getDateTime() {
        return parseDateTime().toLocalDateTime();
    }

    public LocalDate getDate() {
        return parseDateTime().toLocalDate();
    }

    private ZonedDateTime parseDateTime() {
        return dateTime.withZoneSameInstant(DateTimeUtil.ZONE_ID);
    }

}
