package com.benope.apple.api.scrap.bean;

import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapRequestComposite {

    @NotNull
    @Size(min = 1)
    private List<@NotNull Long> scrapNo;
    @NotNull
    private Long directoryNo;

    public List<Scrap> toEntity() {
        return DomainObjectUtil.nullSafeStream(scrapNo)
                .map(it -> Scrap.builder()
                        .scrapNo(it)
                        .parentDirectoryNo(directoryNo)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

}
