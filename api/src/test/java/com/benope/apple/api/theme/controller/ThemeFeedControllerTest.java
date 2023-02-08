package com.benope.apple.api.theme.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.theme.bean.ThemeFeedRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ThemeFeedControllerTest.sql")
public class ThemeFeedControllerTest extends AppleTest {

    @Test
    public void 피드_테마를_조회한다() throws Exception {
        // Then
        ThemeFeedRequest cond = ThemeFeedRequest.builder()
                .themeNo(1L)
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.theme.feed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3))
                .andExpect(jsonPath("$.rst_content[0].feed_no").value(1))
                .andExpect(jsonPath("$.rst_content[1].feed_no").value(3))
                .andExpect(jsonPath("$.rst_content[2].feed_no").value(2));
    }

    @Test
    public void 전체_피드_설정된_테마를_조회한다() throws Exception {
        // Then
        ThemeFeedRequest cond = ThemeFeedRequest.builder()
                .themeNo(2L)
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.theme.feed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3))
                .andExpect(jsonPath("$.rst_content[0].feed_no").value(3))
                .andExpect(jsonPath("$.rst_content[1].feed_no").value(2))
                .andExpect(jsonPath("$.rst_content[2].feed_no").value(1));
    }

}
