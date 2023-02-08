package com.benope.apple.admin.appVersion.bean;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AppVersionResponseV2 {

    private Long id;
    private AppVersion.OS os;
    private String version;
    private boolean forceUpdate;
    private String memo;

    public static AppVersionResponseV2 fromEntity(AppVersion entity) {
        return AppVersionResponseV2.builder()
                .id(entity.getAppVersionNo())
                .os(entity.getOs())
                .version(entity.getVersion())
                .forceUpdate(entity.getForceUpdate())
                .memo(entity.getMemo())
                .build();
    }

}
