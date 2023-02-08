package com.benope.apple.api.theme.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.theme.bean.ThemeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ThemeControllerTest.sql")
public class ThemeControllerTest extends AppleTest {

    @Test
    public void 테마_목록을_조회한다() throws Exception {
        // Given
        ThemeRequest cond = ThemeRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(5)
                .build();

        // Then
        ResultActions resultActions = mockMvc.perform(
                        post("/get.theme")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(5))
                .andExpect(jsonPath("$.rst_content[0].theme_no").value(5))
                .andExpect(jsonPath("$.rst_content[1].theme_no").value(4))
                .andExpect(jsonPath("$.rst_content[2].theme_no").value(3))
                .andExpect(jsonPath("$.rst_content[3].theme_no").value(2))
                .andExpect(jsonPath("$.rst_content[4].theme_no").value(1));
    }

    @Test
    public void 선택된_테마_목록을_조회한다() throws Exception {
        // Then
        ResultActions resultActions = mockMvc.perform(
                        post("/get.theme.picked")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(Map.of()))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].theme_no").value(5))
                .andExpect(jsonPath("$.rst_content[0].feeds[0].feed_no").value(5))
                .andExpect(jsonPath("$.rst_content[0].feeds[1].feed_no").value(4))
                .andExpect(jsonPath("$.rst_content[0].feeds[2].feed_no").value(3))
                .andExpect(jsonPath("$.rst_content[0].feeds[3].feed_no").value(2))
                .andExpect(jsonPath("$.rst_content[0].feeds[4].feed_no").value(1));
    }
}
