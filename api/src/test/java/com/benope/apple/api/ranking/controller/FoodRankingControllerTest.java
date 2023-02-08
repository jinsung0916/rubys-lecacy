package com.benope.apple.api.ranking.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.ranking.bean.FoodRankingRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodRankingControllerTest.sql")
public class FoodRankingControllerTest extends AppleTest {

    @Test
    public void 식품_랭킹을_조회한다() throws Exception {
        // Given
        FoodRankingRequest cond = FoodRankingRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .rankingCriteriaNo(1L)
                .rankDate(LocalDate.of(2022, 11, 29))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.ranking")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3))
                .andExpect(jsonPath("$.rst_content[0].food_no").value(1))
                .andExpect(jsonPath("$.rst_content[0].status").value(2))
                .andExpect(jsonPath("$.rst_content[1].food_no").value(2))
                .andExpect(jsonPath("$.rst_content[1].status").value(0))
                .andExpect(jsonPath("$.rst_content[2].food_no").value(3))
                .andExpect(jsonPath("$.rst_content[2].status").value(-2));
    }

}
