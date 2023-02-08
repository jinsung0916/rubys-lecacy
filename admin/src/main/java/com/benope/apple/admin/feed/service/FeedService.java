package com.benope.apple.admin.feed.service;

import com.benope.apple.domain.feed.bean.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
public interface FeedService {

    Page<Feed> getList(@NotNull Feed feed, @NotNull LocalDate startDate, @NotNull LocalDate endDate, @NotNull Pageable pageable);

    Optional<Feed> getOne(@NotNull Feed feed);

    List<Feed> getByIds(@NotNull List<Long> ids);

}
