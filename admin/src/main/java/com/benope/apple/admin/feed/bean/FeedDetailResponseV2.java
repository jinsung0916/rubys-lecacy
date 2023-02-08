package com.benope.apple.admin.feed.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.feed.bean.FeedDetail;
import com.benope.apple.domain.feed.bean.UploadImgFoodRelation;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedDetailResponseV2 {

    private UploadImgResponseV2 uploadImg_1;
    private UploadImgResponseV2 uploadImg_2;
    private UploadImgResponseV2 uploadImg_3;
    private UploadImgResponseV2 uploadImg_4;
    private UploadImgResponseV2 uploadImg_5;

    private String feedDetailTitle;
    private String feedDetailContent;
    private List<UploadImgFoodRelationResponse> taggedFoods;

    public static FeedDetailResponseV2 fromEntity(FeedDetail detail) {
        return FeedDetailResponseV2.builder()
                .uploadImg_1(
                        UploadImgResponseV2.builder()
                                .imgNo(detail.getUploadImgNo_1())
                                .src(detail.getUploadImgUrl_1())
                                .build()
                )
                .uploadImg_2(
                        UploadImgResponseV2.builder()
                                .imgNo(detail.getUploadImgNo_2())
                                .src(detail.getUploadImgUrl_2())
                                .build()
                )
                .uploadImg_3(
                        UploadImgResponseV2.builder()
                                .imgNo(detail.getUploadImgNo_3())
                                .src(detail.getUploadImgUrl_3())
                                .build()
                )
                .uploadImg_4(
                        UploadImgResponseV2.builder()
                                .imgNo(detail.getUploadImgNo_4())
                                .src(detail.getUploadImgUrl_4())
                                .build()
                )
                .uploadImg_5(
                        UploadImgResponseV2.builder()
                                .imgNo(detail.getUploadImgNo_5())
                                .src(detail.getUploadImgUrl_5())
                                .build()
                )
                .feedDetailTitle(detail.getFeedDetailTitle())
                .feedDetailContent(detail.getFeedDetailContent())
                .taggedFoods(
                        DomainObjectUtil.nullSafeStream(detail.getUploadImgFoodRelations())
                                .map(UploadImgFoodRelationResponse::fromEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UploadImgFoodRelationResponse {
        private Long foodNo;
        private Double userInput;

        public static UploadImgFoodRelationResponse fromEntity(UploadImgFoodRelation entity) {
            return UploadImgFoodRelationResponse.builder()
                    .foodNo(entity.getFoodNo())
                    .userInput(entity.getUserInput())
                    .build();
        }
    }

}
