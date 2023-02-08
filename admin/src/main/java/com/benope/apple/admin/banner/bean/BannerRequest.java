package com.benope.apple.admin.banner.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.banner.bean.Banner;
import com.benope.apple.utils.DateTimeUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BannerRequest implements IdRequest {

    private List<Long> id;

    @NotNull(groups = Create.class)
    private Integer reorderNo;
    @NotNull(groups = Create.class)
    private Long bannerImgNo;
    @URL
    private String linkUrl;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String bannerNm;
    @NotNull(groups = Create.class)
    private ZonedDateTime startDateTime;
    @NotNull(groups = Create.class)
    private ZonedDateTime endDateTime;

    public Banner toEntity(Long bannerNo) {
        return Banner.builder()
                .bannerNo(bannerNo)
                .reorderNo(reorderNo)
                .bannerImgNo(bannerImgNo)
                .linkUrl(linkUrl)
                .bannerNm(bannerNm)
                .startDateTime(parseStartDateTime())
                .endDateTime(parseEndDateTime())
                .build();
    }

    private LocalDateTime parseStartDateTime() {
        return Objects.nonNull(startDateTime)
                ? startDateTime.withZoneSameInstant(DateTimeUtil.ZONE_ID).toLocalDateTime()
                : null;
    }

    private LocalDateTime parseEndDateTime() {
        return Objects.nonNull(endDateTime)
                ? endDateTime.withZoneSameInstant(DateTimeUtil.ZONE_ID).toLocalDateTime()
                : null;
    }

}
