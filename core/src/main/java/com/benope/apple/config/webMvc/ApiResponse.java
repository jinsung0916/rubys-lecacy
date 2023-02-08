package com.benope.apple.config.webMvc;

import com.benope.apple.utils.PagingMetadata;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * API 응답 규격을 정의하는 DTO
 * <p/>
 * 일반적으로, {@link RstCode} 의 {@code toApiResponse} 팩터리 매서드를 사용하는 것을 권장한다.
 *
 * @author AJ
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {

    private final boolean isSuccess = true;
    private String rstCd;
    private String rstMsg;
    private Object rstContent;
    private PagingMetadata pagingMetadata;

    @Builder(access = AccessLevel.PROTECTED)
    protected ApiResponse(RstCode rstCode, Object rstContent, PagingMetadata pagingMetadata) {
        this.rstCd = rstCode.getCode();
        this.rstMsg = rstCode.getMessage();
        this.rstContent = rstContent;
        this.pagingMetadata = pagingMetadata;
    }

}