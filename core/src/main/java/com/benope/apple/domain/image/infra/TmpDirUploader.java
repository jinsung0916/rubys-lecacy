package com.benope.apple.domain.image.infra;

import com.benope.apple.domain.image.repository.Uploader;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Profile("local")
@Service
@RequiredArgsConstructor
public class TmpDirUploader implements Uploader {

    private static final String TMP_DIR = "tmp/";
    private static final String TMP_URL = "http://localhost/";

    @Override
    public URL upload(MultipartFile file) {
        try {
           return doUpload(file);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL doUpload(MultipartFile file) throws IOException {
        File targetFile = new File(TMP_DIR + makeHashedFileName(file.getOriginalFilename()));
        FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
        return new URL(TMP_URL + targetFile.getPath());
    }

}
