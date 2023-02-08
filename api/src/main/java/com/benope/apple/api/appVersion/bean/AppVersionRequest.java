package com.benope.apple.api.appVersion.bean;

import com.benope.apple.domain.appVersion.bean.AppVersion;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AppVersionRequest {

    @NotNull
    private AppVersion.OS os;

    public AppVersion toEntity() {
        return AppVersion.builder()
                .os(os)
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(0, 1, Sort.by(Sort.Order.desc("version")));
    }

}
