package com.benope.apple.admin.theme.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.theme.bean.ThemeFeed;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeFeedRequestV2 implements IdRequest {

    private List<Long> id;

    @NotNull(groups = Create.class)
    private Long themeNo;
    @NotNull(groups = Create.class)
    private Long feedNo;
    @NotNull(groups = Create.class)
    private Long reorderNo;

    public ThemeFeed toEntity(Long themeFeedNo) {
        return ThemeFeed.builder()
                .themeFeedNo(themeFeedNo)
                .themeNo(themeNo)
                .feedNo(feedNo)
                .reorderNo(reorderNo)
                .build();
    }

}
