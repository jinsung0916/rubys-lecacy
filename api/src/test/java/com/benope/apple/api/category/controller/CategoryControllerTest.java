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

@Sql("CategoryControllerTest.sql")
public class CategoryControllerTest extends AppleTest {

    @Test
    void 카테고리_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "current_page_no", 1,
                "records_per_page", 10,
                "category_type_cd", "FOOD"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2));
    }

    @Test
    void 자식_카테고리_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "current_page_no", 1,
                "records_per_page", 10,
                "category_type_cd", "FOOD",
                "parent_category_no", 1
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.category")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1));
    }

}