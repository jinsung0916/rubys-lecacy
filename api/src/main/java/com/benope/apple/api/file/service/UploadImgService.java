package com.benope.apple.api.file.service;

import com.benope.apple.domain.image.bean.UploadImg;
import io.sentry.spring.tracing.SentrySpan;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Validated
@SentrySpan
public interface UploadImgService {

    @NotNull UploadImg uploadFileRegistDb(MultipartFile file);

}
