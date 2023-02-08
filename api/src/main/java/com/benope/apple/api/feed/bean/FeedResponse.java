package com.benope.apple.api.feed.bean;

import com.benope.apple.domain.feed.bean.*;
import com.benope.apple.utils.DomainObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Delegate;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedResponse {

    @Delegate
    @JsonIgnore
    private FeedSimpleResponse feedSimpleResponse;

    private @Setter boolean isLike;
    private @Setter boolean isScrap;

    private List<FeedDetailResponse> feedDetails;

    private NutrientViewResponse nutrientView;

    public static FeedResponse fromEntity(Feed feed) {
        return FeedResponse.builder()
                .feedSimpleResponse(FeedSimpleResponse.fromEntity(feed))
                .feedDetails(toFeedDetailResponse(feed))
                .nutrientView(toNutrientViewResponse(feed))
                .build();
    }

    private static List<FeedDetailResponse> toFeedDetailResponse(Feed feed) {
        return DomainObjectUtil.nullSafeStream(feed.getFeedDetails())
                .map(FeedDetailResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    private static NutrientViewResponse toNutrientViewResponse(Feed feed) {
        List<TaggedFood> foodSummaries =
                DomainObjectUtil.nullSafeStream(feed.getFeedDetails())
                        .map(FeedDetail::getItemTag)
                        .flatMap(List::stream)
                        .collect(Collectors.toUnmodifiableList());

        NutrientView nutrientView = new TaggedFoodComposite(foodSummaries);
        return NutrientViewResponse.fromEntity(nutrientView);
    }
}
