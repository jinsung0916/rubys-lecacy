package com.benope.apple.api.report.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.report.bean.FoodReportRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FoodReportControllerTest extends AppleTest {

    @Test
    public void 식품_DB_등록을_요청한다() throws Exception {
        // Given
        FoodReportRequest cond = FoodReportRequest.builder()
                .contents_1("식품")
                .contents_2("DB")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.food.report")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.contents_1").value("식품"))
                .andExpect(jsonPath("$.rst_content.contents_2").value("DB"));
    }

}
