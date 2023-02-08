package com.benope.apple.api.feed.bean;

import com.benope.apple.api.food.bean.FoodResponse;
import com.benope.apple.domain.feed.bean.FeedDetail;
import com.benope.apple.domain.feed.bean.TaggedFood;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Delegate;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedDetailResponse {

    private Long uploadImgNo_1;
    private Long uploadImgNo_2;
    private Long uploadImgNo_3;
    private Long uploadImgNo_4;
    private Long uploadImgNo_5;
    private String uploadImgUrl_1;
    private String uploadImgUrl_2;
    private String uploadImgUrl_3;
    private String uploadImgUrl_4;
    private String uploadImgUrl_5;

    private List<TaggedFoodResponse> itemTag;

    private String feedDetailTitle;
    private String feedDetailContent;

    public static FeedDetailResponse fromEntity(FeedDetail detail) {
        return FeedDetailResponse.builder()
                .uploadImgNo_1(detail.getUploadImgNo_1())
                .uploadImgNo_2(detail.getUploadImgNo_2())
                .uploadImgNo_3(detail.getUploadImgNo_3())
                .uploadImgNo_4(detail.getUploadImgNo_4())
                .uploadImgNo_5(detail.getUploadImgNo_5())
                .uploadImgUrl_1(detail.getUploadImgUrl_1())
                .uploadImgUrl_2(detail.getUploadImgUrl_2())
                .uploadImgUrl_3(detail.getUploadImgUrl_3())
                .uploadImgUrl_4(detail.getUploadImgUrl_4())
                .uploadImgUrl_5(detail.getUploadImgUrl_5())
                .itemTag(toItemTagResponse(detail))
                .feedDetailTitle(detail.getFeedDetailTitle())
                .feedDetailContent(detail.getFeedDetailContent())
                .build();
    }

    private static List<TaggedFoodResponse> toItemTagResponse(FeedDetail detail) {
        List<TaggedFoodResponse> responses = new ArrayList<>();

        for (TaggedFood taggedFood : detail.getItemTag()) {
            try {
                responses.add(TaggedFoodResponse.withTaggedFood(taggedFood));
            } catch (EntityNotFoundException e) {
                // bypass
            }
        }

        return responses;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TaggedFoodResponse {

        @Delegate
        @JsonIgnore
        private FoodResponse foodResponse;
        private Double userInput;

        public static TaggedFoodResponse withTaggedFood(TaggedFood taggedFood) {
            return TaggedFoodResponse.builder()
                    .foodResponse(FoodResponse.fromEntity(taggedFood.getFood()))
                    .userInput(taggedFood.getUserInput())
                    .build();
        }
    }

}
