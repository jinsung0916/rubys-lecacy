package com.benope.apple.admin.feed.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.admin.feed.bean.FeedRequestV2;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FeedControllerTest.sql")
class FeedControllerV2Test extends AppleAdminTest {

    @Test
    void ID로_피드_목록을_조회한다() throws Exception {
        // Given
        FeedRequestV2 cond = FeedRequestV2.builder()
                .id(List.of(1L, 2L, 3L))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/feed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    void 피드_상세를_조회한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/feed/3")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.feed_details.size()").value(2))
                .andExpect(jsonPath("$.feed_details[0].tagged_foods.size()").value(1))
                .andExpect(jsonPath("$.feed_details[1].tagged_foods.size()").value(1));
    }

    @Test
    void 피드가_등록된_테마를_조회한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/feed/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.theme_no").value(1));
    }

}