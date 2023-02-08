package com.benope.apple.admin.category.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.config.webMvc.RstCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("CategoryControllerTest.sql")
class FoodCategoryControllerV2Test extends AppleAdminTest {

    @Test
    void 카테고리_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "parent_category_no", 0
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/category/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void 자식_카테고리_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "parent_category_no", 1
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/category/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void ID로_카테고리_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "id", List.of(1, 2, 3, 4)
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/category/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(3));
    }

    @Test
    void 제품_카테고리를_생성한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "category_nm", "created"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/category/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.category_nm").value("created"))
                .andExpect(jsonPath("$.parent_category_no").value(0))
                .andExpect(jsonPath("$.category_type_cd").value("FOOD"));
    }

    @Test
    void 자식_카테고리를_생성한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "parent_category_no", 2,
                "category_nm", "created"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/category/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.category_nm").value("created"))
                .andExpect(jsonPath("$.parent_category_no").value(2))
                .andExpect(jsonPath("$.category_type_cd").value("FOOD"));
    }

    @Test
    void 제품_카테고리를_수정한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "category_nm", "updated"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/category/food/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category_nm").value("updated"));
    }

    @Test
    void 카테고리를_삭제한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/category/food/2")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value(2));
    }

    @Test
    void 자식이_존재하는_카테고리를_삭제할_경우_400을_반환한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/category/food/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.CHILD_CATEGORY_EXISTS));
    }

    @Test
    void 식품_DB가_등록된_카테고리를_삭제할_경우_400을_반환한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/category/food/4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.FOOD_EXISTS));
    }
}