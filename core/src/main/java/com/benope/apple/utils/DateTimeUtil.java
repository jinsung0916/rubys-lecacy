package com.benope.apple.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class DateTimeUtil {

    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Seoul");

    public static LocalDateTime getCurrentDateTime() {
        return ZonedDateTime.now(ZONE_ID).toLocalDateTime();
    }

    public static LocalDate getCurrentDate() {
        return ZonedDateTime.now(ZONE_ID).toLocalDate();
    }

    public static String getTimestamp() {
        Timestamp timestamp = Timestamp.valueOf(getCurrentDateTime());
        return Objects.toString(timestamp);
    }

}
