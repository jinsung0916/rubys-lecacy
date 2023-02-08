package com.benope.apple.api.appVersion.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.appVersion.bean.AppVersionRequest;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("AppVersionControllerTest.sql")
public class AppVersionControllerTest extends AppleTest {

    @Test
    public void 앱_버전을_조회한다() throws Exception {
        // Given
        AppVersionRequest cond = AppVersionRequest.builder()
                .os(AppVersion.OS.IOS)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.app.version")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.os").value("IOS"))
                .andExpect(jsonPath("$.rst_content.version").value("1.0.2"))
                .andExpect(jsonPath("$.rst_content.force_update").value(true));
    }

}