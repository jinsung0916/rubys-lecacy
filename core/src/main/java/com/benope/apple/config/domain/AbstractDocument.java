package com.benope.apple.config.domain;

import com.benope.apple.utils.DateTimeUtil;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.solr.core.mapping.Indexed;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDocument {

    @Indexed(name = "_text_", type = "text_ko", stored = false)
    private List<String> text;
    @Indexed(name = "_text_str_", type = "strings", stored = false)
    private List<String> textStr;

    @Indexed(type = "pdate")
    private LocalDateTime createdAt;
    @Indexed(type = "plong")
    private Long createdBy;
    @Indexed(type = "pdate")
    private LocalDateTime updatedAt;
    @Indexed(type = "plong")
    private Long updatedBy;
    @Indexed(type = "string")
    private RowStatCd rowStatCd;

    public List<String> getText() {
        return DomainObjectUtil.nullSafeStream(this.text)
                .map(it -> it.replaceAll(" ", "\t"))
                .collect(Collectors.toUnmodifiableList());
    }

    public final ZonedDateTime getZonedCreateAt() {
        return DomainObjectUtil.nullSafeFunction(this.createdAt, it -> ZonedDateTime.of(it, DateTimeUtil.ZONE_ID));
    }

    public final ZonedDateTime getZonedUpdatedAt() {
        return DomainObjectUtil.nullSafeFunction(this.updatedAt, it -> ZonedDateTime.of(it, DateTimeUtil.ZONE_ID));
    }

}
