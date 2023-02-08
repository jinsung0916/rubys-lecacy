package com.benope.apple.admin.banner.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.banner.bean.Banner;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerResponse {

    private Long id;
    private Integer reorderNo;
    private UploadImgResponseV2 bannerImg;
    private String linkUrl;
    private String bannerNm;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    public static BannerResponse fromEntity(Banner banner) {
        return BannerResponse.builder()
                .id(banner.getBannerNo())
                .reorderNo(banner.getReorderNo())
                .bannerImg(
                        UploadImgResponseV2.builder()
                                .imgNo(banner.getBannerImgNo())
                                .src(banner.getBannerImgUrl())
                                .build()
                )
                .linkUrl(banner.getLinkUrl())
                .bannerNm(banner.getBannerNm())
                .startDateTime(ZonedDateTime.of(banner.getStartDateTime(), DateTimeUtil.ZONE_ID))
                .endDateTime(ZonedDateTime.of(banner.getEndDateTime(), DateTimeUtil.ZONE_ID))
                .build();
    }

}
