package com.benope.apple.admin.appVersion.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AppVersionRequestV2 implements IdRequest {

    private List<Long> id;

    @NotNull(groups = Create.class)
    private AppVersion.OS os;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String version;
    @NotNull(groups = Create.class)
    private Boolean forceUpdate;
    private String memo;

    public AppVersion toEntity(Long appVersionNo) {
        return AppVersion.builder()
                .appVersionNo(appVersionNo)
                .os(os)
                .version(version)
                .forceUpdate(forceUpdate)
                .memo(memo)
                .build();
    }

}
