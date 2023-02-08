package com.benope.apple.api.banner.bean;

import com.benope.apple.domain.banner.bean.Banner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerRequest {

    public Banner toEntity() {
        return Banner.builder()
                .build();
    }

    public Pageable toPageable() {
        // 상위 10 개만 노출
        return PageRequest.of(0, 10, Sort.by(Sort.Order.asc("reorderNo")));
    }

}
