package com.benope.apple.api.theme.bean;

import com.benope.apple.domain.theme.bean.Theme;
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
public class ThemePickedRequest {

    private static final int DEFAULT_PAGE_SIZE = 2;

    public Theme toEntity() {
        return Theme.builder()
                .picked(true)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.ofSize(DEFAULT_PAGE_SIZE).withSort(Sort.by(Sort.Order.asc("reorderNo"), Sort.Order.asc("themeNo")));
    }

}
