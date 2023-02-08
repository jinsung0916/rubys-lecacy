package com.benope.apple.api.file.controller;

import com.benope.apple.api.file.bean.UploadImgResponse;
import com.benope.apple.api.file.service.UploadImgService;
import com.benope.apple.config.webMvc.ApiResponse;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.image.bean.UploadImg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FileUploadController {

    private final UploadImgService uploadImgService;

    /**
     * S3에 파일을 업로드하고 DB 에 정보를 등록한다
     */
    @RequestMapping(value = "/upload.file.reg.db", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse uploadFileRegDb(
            @RequestPart MultipartFile file
    ) {
        UploadImg uploadImg = uploadImgService.uploadFileRegistDb(file);
        return RstCode.SUCCESS.toApiResponse(UploadImgResponse.fromEntity(uploadImg));
    }
}
