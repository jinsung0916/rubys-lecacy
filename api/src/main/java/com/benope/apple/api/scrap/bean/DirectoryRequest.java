package com.benope.apple.api.scrap.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class DirectoryRequest {

    @NotNull(groups = Delete.class)
    private Long directoryNo;
    @NotBlank(groups = Create.class)
    private String directoryName;

    public Scrap toEntity() {
        return Scrap.builder()
                .scrapNo(directoryNo)
                .scrapTypeCd(ScrapTypeCd.DIRECTORY)
                .mbNo(SessionUtil.getSessionMbNo())
                .directoryName(directoryName)
                .build();
    }

}
