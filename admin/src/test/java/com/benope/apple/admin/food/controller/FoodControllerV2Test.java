package com.benope.apple.admin.food.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.config.webMvc.RstCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodControllerTest.sql")
class FoodControllerV2Test extends AppleAdminTest {

    @Test
    void 식품을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "food_nm", "닭가슴살스테이크"
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/food")
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
                .andExpect(jsonPath("$.size()").value(5));
    }

    @Test
    void 식품을_정렬한다_카테고리() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "food_nm", "닭가슴살스테이크"
        );

        List<Integer> range = List.of(0, 10);

        List<String> sort = List.of("category.parent.category_nm", "DESC");

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                                .param("sort", mapper.writeValueAsString(sort))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(5));
    }

    @Test
    void 식품을_정렬한다_세부_카테고리() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "food_nm", "닭가슴살스테이크"
        );

        List<Integer> range = List.of(0, 10);

        List<String> sort = List.of("category.category_nm", "DESC");

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                                .param("sort", mapper.writeValueAsString(sort))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(5));
    }

    @Test
    void 식품을_등록한다() throws Exception {
        // Given
        Map<String, Object> cond = new HashMap<>(Map.of(
                "food_nm", "20bar",
                "brand", "(주)배노프하우스",
                "sub_category_no", 108,
                "product_status_cd", "FOR_SALE",
                "storage_type", "실온",
                "writer", "AJ"
        ));

        cond.putAll(
                Map.of(
                        "weight", 1,
                        "weight_unit_cd", "MG",
                        "per_serving", 1,
                        "per_serving_unit_cd", "G",
                        "nutrient_standards", 1,
                        "nutrient_standards_unit_cd", "KG"
                )
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void 식품_단위가_일치하지_않을_경우_400을_반환한다() throws Exception {
        // Given
        Map<String, Object> cond = new HashMap<>(Map.of(
                "food_nm", "20bar",
                "brand", "(주)배노프하우스",
                "sub_category_no", 108,
                "product_status_cd", "FOR_SALE",
                "storage_type", "실온",
                "writer", "AJ"
        ));

        cond.putAll(
                Map.of(
                        "weight", 1,
                        "weight_unit_cd", "G",
                        "per_serving", 1,
                        "per_serving_unit_cd", "KG",
                        "nutrient_standards", 1,
                        "nutrient_standards_unit_cd", "ML"
                )
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.UNIT_CD_NOT_MATCHED));
    }

    @Test
    void 식품을_갱신한다() throws Exception {
        // Given
        Map<String, Object> cond = new HashMap<>(Map.of(
                "food_no", 1,
                "food_nm", "20bar",
                "brand", "(주)배노프하우스",
                "sub_category_no", 108,
                "product_status_cd", "FOR_SALE",
                "storage_type", "실온",
                "writer", "AJ"
        ));

        cond.putAll(
                Map.of(
                        "weight", 1,
                        "weight_unit_cd", "G",
                        "per_serving", 1,
                        "per_serving_unit_cd", "G",
                        "nutrient_standards", 1,
                        "nutrient_standards_unit_cd", "G"
                )
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/food/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.front_img.img_no").isEmpty());
    }

    @Test
    void 식품을_삭제한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/food/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value(1));
    }


}