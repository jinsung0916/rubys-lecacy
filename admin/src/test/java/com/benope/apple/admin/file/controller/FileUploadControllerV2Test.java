package com.benope.apple.admin.file.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.domain.image.repository.Uploader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Sql("FileUploadControllerTest.sql")
class FileUploadControllerV2Test extends AppleAdminTest {

    @MockBean
    private Uploader uploader;

    @Test
    public void 파일을_업로드한다() throws Exception {
        // Given
        ClassPathResource resource = new ClassPathResource("image.jpg");
        MockMultipartFile file = new MockMultipartFile("file", resource.getInputStream());

        given(uploader.upload(any()))
                .willReturn(new URL("https://www.test.com/test/image.jpg"));

        // When
        ResultActions resultActions = mockMvc.perform(
                        multipart("/file")
                                .file(file)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.src").value(UploadImg.BASE_URL + "/test/image.jpg"));
    }

}