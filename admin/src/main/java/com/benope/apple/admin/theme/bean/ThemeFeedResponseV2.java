package com.benope.apple.admin.theme.bean;

import com.benope.apple.domain.theme.bean.ThemeFeed;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class ThemeFeedResponseV2 {
    private Long id;
    private Long themeNo;
    private Long reorderNo;
    private Long feedNo;

    private ZonedDateTime createdAt;

    public static ThemeFeedResponseV2 fromEntity(ThemeFeed entity) {
        return ThemeFeedResponseV2.builder()
                .id(entity.getThemeFeedNo())
                .themeNo(entity.getThemeNo())
                .reorderNo(entity.getReorderNo())
                .feedNo(entity.getFeedNo())
                .createdAt(entity.getZonedCreateAt())
                .build();
    }
}
