package com.benope.apple.domain.image.repository;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.time.LocalDateTime;

public interface Uploader {

    @NotNull URL upload(MultipartFile file);

    String FILE_SEPARATOR = "/";

    /**
     * bucket key 값을 생성한다.
     *
     * @param originalFileName 원본파일명
     * @return 년/월/일/해쉬된 파일 키값(타임스탬프 + 원본파일명)
     */
    default String makeHashedFileName(String originalFileName) {
        LocalDateTime now = LocalDateTime.now();
        return now.getYear() +
                FILE_SEPARATOR +
                now.getMonthValue() +
                FILE_SEPARATOR +
                now.getDayOfMonth() +
                FILE_SEPARATOR +
                doMakeHashedFileName(now, originalFileName);
    }

    /**
     * 현재 시간 타임스탬프와 원본 파일명을 SHA256 해쉬값으로 업로드 파일키 값을 생성한다.
     *
     * @param originalFileName 원본 파일명
     * @return 해쉬된 파일 키값
     */
    private String doMakeHashedFileName(LocalDateTime now, String originalFileName) {
        return DigestUtils.sha256Hex(now.toString() + originalFileName);
    }

}
