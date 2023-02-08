package com.benope.apple.admin.file.bean;

import com.benope.apple.domain.image.bean.UploadImg;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImgResponseV2 {

    private Long imgNo;
    private String src;
    private String title;

    public static UploadImgResponseV2 fromEntity(UploadImg uploadImg) {
        return UploadImgResponseV2.builder()
                .imgNo(uploadImg.getImgNo())
                .src(uploadImg.getImgFileUrl())
                .title(uploadImg.getImgFileNm())
                .build();
    }

}
