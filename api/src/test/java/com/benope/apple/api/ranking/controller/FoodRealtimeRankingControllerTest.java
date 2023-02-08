package com.benope.apple.api.ranking.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.ranking.bean.FoodRealtimeRankingRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodRealtimeRankingControllerTest.sql")
public class FoodRealtimeRankingControllerTest extends AppleTest {

    @Test
    public void 식품_실시간_랭킹을_조회한다() throws Exception {
        // Given
        FoodRealtimeRankingRequest cond = FoodRealtimeRankingRequest.builder()
                .rankDate(LocalDate.of(2023, 1, 3))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.realtime.ranking")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(6))
                .andExpect(jsonPath("$.rst_content[0].food_no").value(1))
                .andExpect(jsonPath("$.rst_content[1].food_no").value(2))
                .andExpect(jsonPath("$.rst_content[2].food_no").value(3))
                .andExpect(jsonPath("$.rst_content[3].food_no").value(4))
                .andExpect(jsonPath("$.rst_content[4].food_no").value(5))
                .andExpect(jsonPath("$.rst_content[5].food_no").value(6));
    }

}
