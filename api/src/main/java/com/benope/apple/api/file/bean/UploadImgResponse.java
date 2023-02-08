package com.benope.apple.api.file.bean;

import com.benope.apple.domain.image.bean.UploadImg;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImgResponse {

    private Long imgNo;
    private String imgFileNm;
    private String imgFileExt;
    private String imgFilePath;
    private String imgFileUrl;

    public static UploadImgResponse fromEntity(UploadImg uploadImg) {
        return UploadImgResponse.builder()
                .imgNo(uploadImg.getImgNo())
                .imgFileNm(uploadImg.getImgFileNm())
                .imgFileExt(uploadImg.getImgFileExt())
                .imgFilePath(uploadImg.getImgFilePath())
                .imgFileUrl(uploadImg.getImgFileUrl())
                .build();
    }

}
