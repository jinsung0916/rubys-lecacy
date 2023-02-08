package com.benope.apple.api.follow.bean;

import com.benope.apple.domain.follow.bean.Follow;
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
public class FollowSearchRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    public Follow toFollowerSearch() {
        return Follow.builder()
                .followMbNo(SessionUtil.getSessionMbNo())
                .build();
    }

    public Follow toFollowingSearch() {
        return Follow.builder()
                .mbNo(SessionUtil.getSessionMbNo())
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.desc("followNo")));
    }

}
