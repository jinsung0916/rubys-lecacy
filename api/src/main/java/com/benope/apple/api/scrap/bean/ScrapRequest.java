package com.benope.apple.api.scrap.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Update;
import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapRequest {

    @NotNull(groups = Update.class)
    private Long scrapNo;
    @NotNull(groups = Update.class)
    private Long directoryNo;
    @NotNull(groups = {Create.class, Delete.class})
    private Long feedNo;

    public Scrap toEntity() {
        return Scrap.builder()
                .scrapNo(scrapNo)
                .scrapTypeCd(ScrapTypeCd.SCRAP)
                .parentDirectoryNo(directoryNo)
                .mbNo(SessionUtil.getSessionMbNo())
                .feedNo(feedNo)
                .build();
    }

}
