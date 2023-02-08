package com.benope.apple.api.banner.bean;

import com.benope.apple.domain.banner.bean.Banner;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerResponse {

    private Long bannerNo;
    private Integer reorderNo;
    private String bannerImgUrl;
    private String linkUrl;
    private String bannerNm;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    public static BannerResponse fromEntity(Banner banner) {
        return BannerResponse.builder()
                .bannerNo(banner.getBannerNo())
                .reorderNo(banner.getReorderNo())
                .bannerImgUrl(banner.getBannerImgUrl())
                .linkUrl(banner.getLinkUrl())
                .bannerNm(banner.getBannerNm())
                .startDateTime(ZonedDateTime.of(banner.getStartDateTime(), DateTimeUtil.ZONE_ID))
                .endDateTime(ZonedDateTime.of(banner.getEndDateTime(), DateTimeUtil.ZONE_ID))
                .build();
    }

}
