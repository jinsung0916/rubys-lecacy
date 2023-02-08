package com.benope.apple.config.webMvc;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Objects;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class EnumResponse {

    private String code;
    private String desc;

    public static @Nullable EnumResponse fromEntity(EnumDescribed described) {
        return Objects.nonNull(described)
                ? EnumResponse.builder().code(described.getCode()).desc(described.getDesc()).build()
                : null;
    }

}
