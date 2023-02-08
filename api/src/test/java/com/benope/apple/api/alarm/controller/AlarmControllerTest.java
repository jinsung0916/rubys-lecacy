package com.benope.apple.api.alarm.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.alarm.bean.AlarmRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("AlarmControllerTest.sql")
public class AlarmControllerTest extends AppleTest {

    @Test
    public void 알람을_조회한다() throws Exception {
        // Given
        AlarmRequest cond = AlarmRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.alarm")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(5))
                .andExpect(jsonPath("$.rst_content[0].read").value(false))
                .andExpect(jsonPath("$.rst_content[1].read").value(false))
                .andExpect(jsonPath("$.rst_content[2].read").value(true))
                .andExpect(jsonPath("$.rst_content[3].read").value(true))
                .andExpect(jsonPath("$.rst_content[4].read").value(true));
    }

    @Test
    public void 읽지_않은_알람_갯수를_조회한다() throws Exception {
        // Given
        AlarmRequest cond = AlarmRequest.builder()
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.alarm.count")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.count").value(5));
    }
}
