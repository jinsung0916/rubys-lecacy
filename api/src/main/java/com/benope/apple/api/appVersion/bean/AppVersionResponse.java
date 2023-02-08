package com.benope.apple.api.appVersion.bean;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AppVersionResponse {

    private AppVersion.OS os;
    private String version;
    private boolean forceUpdate;

    public static AppVersionResponse fromEntity(AppVersion entity) {
        return AppVersionResponse.builder()
                .os(entity.getOs())
                .version(entity.getVersion())
                .forceUpdate(entity.getForceUpdate())
                .build();
    }

}
