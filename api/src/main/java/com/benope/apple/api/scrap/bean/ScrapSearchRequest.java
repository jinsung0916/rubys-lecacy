package com.benope.apple.api.scrap.bean;

import com.benope.apple.domain.scrap.bean.Scrap;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import com.benope.apple.utils.SessionUtil;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ScrapSearchRequest {

    @NotNull
    @Min(1)
    private Integer currentPageNo;
    @NotNull
    @Min(1)
    private Integer recordsPerPage;

    private ScrapTypeCd type;
    private Long directoryNo;

    public Scrap toEntity() {
        return Scrap.builder()
                .scrapTypeCd(type)
                .mbNo(SessionUtil.getSessionMbNo())
                .parentDirectoryNo(Objects.requireNonNullElse(directoryNo, Scrap.INITIAL_PARENT_DIRECTORY_NO))
                .build();
    }

    public Pageable toPageable() {
        return PageRequest.of(currentPageNo - 1, recordsPerPage, Sort.by(Sort.Order.asc("scrapTypeCd"), Sort.Order.desc("scrapNo")));
    }
}
