package com.benope.apple.admin.file.serviceImpl;

import com.benope.apple.admin.file.service.UploadImgService;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.domain.image.repository.UploadImgJpaRepository;
import com.benope.apple.domain.image.repository.Uploader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadImgServiceImpl implements UploadImgService {

    private final UploadImgJpaRepository repository;

    private final Uploader uploader;

    @Override
    public UploadImg uploadFileRegistDb(MultipartFile file) {
        URL url = uploader.upload(file);

        UploadImg uploadImg = UploadImg.builder()
                .imgFileNm(file.getOriginalFilename())
                .imgFileExt(FilenameUtils.getExtension(file.getOriginalFilename()))
                .imgFilePath(url.getPath())
                .build();

        return repository.save(uploadImg);
    }
}
