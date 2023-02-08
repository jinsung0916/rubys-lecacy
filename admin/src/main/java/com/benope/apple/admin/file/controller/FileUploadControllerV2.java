package com.benope.apple.admin.file.controller;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.admin.file.service.UploadImgService;
import com.benope.apple.domain.image.bean.UploadImg;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.benope.apple.config.webMvc.WebMvcConfig.V2_HEADER;

@RestController
@RequestMapping(value = "/file", headers = V2_HEADER)
@RequiredArgsConstructor
public class FileUploadControllerV2 {

    private final UploadImgService uploadImgService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadImgResponseV2 create(
            @RequestPart MultipartFile file
    ) {
        UploadImg uploadImg = uploadImgService.uploadFileRegistDb(file);
        return UploadImgResponseV2.fromEntity(uploadImg);
    }

}
