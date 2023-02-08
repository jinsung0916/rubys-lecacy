package com.benope.apple.api.report.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.report.bean.ReportRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReportControllerTest extends AppleTest {

    @Test
    public void 피드를_신고한다() throws Exception {
        // Given
        ReportRequest cond = ReportRequest.builder()
                .reportTargetCd(ReportRequest.ReportTargetCdRequest.FEED)
                .reportTargetNo(1L)
                .contents("qwerty")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.report")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.report_target_cd").value("FEED"))
                .andExpect(jsonPath("$.rst_content.report_target_no").value(1L))
                .andExpect(jsonPath("$.rst_content.contents").value("qwerty"));
    }

    @Test
    public void 피드_댓글을_신고한다() throws Exception {
        // Given
        ReportRequest cond = ReportRequest.builder()
                .reportTargetCd(ReportRequest.ReportTargetCdRequest.FEED_COMMENT)
                .reportTargetNo(1L)
                .contents("qwerty")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.report")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.report_target_cd").value("COMMENT"))
                .andExpect(jsonPath("$.rst_content.report_target_no").value(1L))
                .andExpect(jsonPath("$.rst_content.contents").value("qwerty"));
    }

}
