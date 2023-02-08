package com.benope.apple.api.theme.bean;

import com.benope.apple.domain.theme.bean.Theme;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ThemeResponse {

    private Long themeNo;
    private String displayNm;
    private Long reorderNo;

    private List<ThemeFeedResponse> feeds;

    public static ThemeResponse fromEntity(Theme theme) {
        return ThemeResponse.builder()
                .themeNo(theme.getThemeNo())
                .displayNm(theme.getDisplayNm())
                .reorderNo(theme.getReorderNo())
                .build();
    }

    public void setThemeFeeds(List<ThemeFeed> themeFeeds) {
        this.feeds = DomainObjectUtil.nullSafeStream(themeFeeds)
                .map(ThemeFeed::getFeed)
                .map(ThemeFeedResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }
}
