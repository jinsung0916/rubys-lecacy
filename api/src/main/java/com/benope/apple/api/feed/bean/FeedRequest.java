package com.benope.apple.api.feed.bean;

import com.benope.apple.config.validation.Create;
import com.benope.apple.config.validation.Delete;
import com.benope.apple.config.validation.Read;
import com.benope.apple.config.validation.Update;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import com.benope.apple.utils.DomainObjectUtil;
import com.benope.apple.utils.SessionUtil;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedRequest {

    @NotNull(groups = {Read.class, Update.class, Delete.class})
    private Long feedNo;
    @NotNull(groups = Create.class)
    private FeedTypeCd feedTypeCd;
    private String feedTitle;
    @NotNull(groups = Create.class)
    private Long rpstImgNo;
    private List<String> hashTag;

    @NotNull(groups = Create.class)
    @Size(min = 1, max = 5, groups = Create.class)
    private @Valid List<FeedDetailRequest> feedDetails;

    public Feed toEntity() {
        return Feed.builder()
                .feedNo(feedNo)
                .mbNo(SessionUtil.getSessionMbNo())
                .feedTypeCd(feedTypeCd)
                .feedTitle(feedTitle)
                .rpstImgNo(rpstImgNo)
                .hashTag(hashTag)
                .feedDetails(
                        DomainObjectUtil
                                .nullSafeStream(feedDetails)
                                .map(FeedDetailRequest::toEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .build();
    }

}
