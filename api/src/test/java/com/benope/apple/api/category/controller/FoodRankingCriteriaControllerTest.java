package com.benope.apple.api.category.controller;

import com.benope.apple.api.AppleTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodRankingCriteriaControllerTest.sql")
public class FoodRankingCriteriaControllerTest extends AppleTest {

    @Test
    public void 식품_랭킹_기준을_조회한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.ranking.criteria")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(Map.of()))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(10));
    }

}
