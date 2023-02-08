package com.benope.apple.domain.banner.repository;

import com.benope.apple.domain.banner.bean.QBanner;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;

import java.time.LocalDateTime;

public class BannerQuerydslPredicates {

    private static final QBanner qBanner = QBanner.banner;

    public static Predicate currentDateTimeBetween(LocalDateTime currentDateTime) {
        return Expressions.asDateTime(currentDateTime).between(qBanner.startDateTime, qBanner.endDateTime);
    }

}
