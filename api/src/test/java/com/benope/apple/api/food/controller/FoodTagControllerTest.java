package com.benope.apple.api.food.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.food.bean.FoodTagRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodTagControllerTest.sql")
public class FoodTagControllerTest extends AppleTest {

    @Test
    public void 태그_목록을_조회한다() throws Exception {
        // Given
        FoodTagRequest cond = FoodTagRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .searchMbNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.tag")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3))
                .andExpect(jsonPath("$.rst_content[0].food_no").value(3))
                .andExpect(jsonPath("$.rst_content[1].food_no").value(2))
                .andExpect(jsonPath("$.rst_content[2].food_no").value(1));
    }

}
