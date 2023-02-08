package com.benope.apple.domain.image.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.benope.apple.domain.image.repository.Uploader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@ConditionalOnWebApplication
@Profile("!local")
@Service
@Slf4j
public class S3Uploader implements Uploader {

    private static final String FILE_SEPARATOR = "/";

    private final AmazonS3 amazonS3;
    private final String bucket;

    public S3Uploader(
            AmazonS3 amazonS3,
            @Value("${cloud.aws.s3.bucket}") String bucket
    ) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    @Override
    public URL upload(MultipartFile file) {
        final String hashedS3ObjectKey = makeHashedFileName(file.getOriginalFilename());
        String rootDirName = UploadFileType.getDirNameByFileType(file.getOriginalFilename());
        String fileName = rootDirName + FILE_SEPARATOR + hashedS3ObjectKey;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream is = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, is, objectMetadata));
        } catch (IOException e) {
            throw new IllegalStateException("S3 파일 업로드 실패", e);
        }

        return amazonS3.getUrl(bucket, fileName);
    }

}
