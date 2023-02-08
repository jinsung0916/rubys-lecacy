package com.benope.apple.api.feed.bean;

import com.benope.apple.domain.feed.bean.FeedDetail;
import com.benope.apple.domain.feed.bean.UploadImgFoodRelation;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedDetailRequest {

    private Long uploadImgNo_1;
    private Long uploadImgNo_2;
    private Long uploadImgNo_3;
    private Long uploadImgNo_4;
    private Long uploadImgNo_5;

    @Size(max = 10)
    private @Valid List<ItemTag> itemTag;

    private String feedDetailTitle;
    @NotBlank
    private String feedDetailContent;

    public FeedDetail toEntity() {
        return FeedDetail.builder()
                .uploadImgNo_1(uploadImgNo_1)
                .uploadImgNo_2(uploadImgNo_2)
                .uploadImgNo_3(uploadImgNo_3)
                .uploadImgNo_4(uploadImgNo_4)
                .uploadImgNo_5(uploadImgNo_5)
                .uploadImgFoodRelation_1(
                        DomainObjectUtil.nullSafeStream(itemTag).map(ItemTag::toRelation).collect(Collectors.toUnmodifiableList())
                )
                .feedDetailTitle(feedDetailTitle)
                .feedDetailContent(feedDetailContent)
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ItemTag {
        @NotNull
        private Long foodNo;
        @Min(0)
        private Double userInput;

        public UploadImgFoodRelation toRelation() {
            return UploadImgFoodRelation.builder()
                    .foodNo(foodNo)
                    .userInput(userInput)
                    .build();
        }
    }

}
