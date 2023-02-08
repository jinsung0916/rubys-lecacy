package com.benope.apple.api.food.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.food.bean.FoodRandomRequest;
import com.benope.apple.api.food.bean.FoodRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodControllerTest.sql")
public class FoodControllerTest extends AppleTest {

    @Test
    public void 식품_번호로_식품을_조회한다() throws Exception {
        // Given
        FoodRequest cond = FoodRequest.builder()
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.food_no").value(1))
                .andExpect(jsonPath("$.rst_content.food_nm").value("닭가슴살스테이크오리지널"))
                .andExpect(jsonPath("$.rst_content.brand").value("맛있닭"))
                .andExpect(jsonPath("$.rst_content.food_details[0].calories").value(280))
                .andExpect(jsonPath("$.rst_content.food_details[0].calories_unit_cd").value("kcal"))
                .andExpect(jsonPath("$.rst_content.food_details[0].nutrient_standards").value(70))
                .andExpect(jsonPath("$.rst_content.food_details[0].nutrient_standards_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].nateulyum").value(400))
                .andExpect(jsonPath("$.rst_content.food_details[0].nateulyum_unit_cd").value("mg"))
                .andExpect(jsonPath("$.rst_content.food_details[0].nateulyum_ratio").value("20%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].carbohydrate").value(33))
                .andExpect(jsonPath("$.rst_content.food_details[0].carbohydrate_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].carbohydrate_ratio").value("10%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].sugars").value(15))
                .andExpect(jsonPath("$.rst_content.food_details[0].sugars_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].sugars_ratio").value("15%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].protein").value(20))
                .andExpect(jsonPath("$.rst_content.food_details[0].protein_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].protein_ratio").value("36%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].fat").value(8))
                .andExpect(jsonPath("$.rst_content.food_details[0].fat_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].fat_ratio").value("15%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].saturated_fat").value(4))
                .andExpect(jsonPath("$.rst_content.food_details[0].saturated_fat_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].saturated_fat_ratio").value("27%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].trans_fat").value(0))
                .andExpect(jsonPath("$.rst_content.food_details[0].trans_fat_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].cholesterol").value(0))
                .andExpect(jsonPath("$.rst_content.food_details[0].cholesterol_unit_cd").value("mg"))
                .andExpect(jsonPath("$.rst_content.food_details[0].cholesterol_ratio").value("0%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].dietary_fiber").value(1))
                .andExpect(jsonPath("$.rst_content.food_details[0].dietary_fiber_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].dietary_fiber_ratio").value("4%"))
                .andExpect(jsonPath("$.rst_content.food_details[0].allulose").value(1))
                .andExpect(jsonPath("$.rst_content.food_details[0].allulose_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].erythritol").value(1))
                .andExpect(jsonPath("$.rst_content.food_details[0].erythritol_unit_cd").value("g"))
                .andExpect(jsonPath("$.rst_content.food_details[0].sugar_alcohol").value(1))
                .andExpect(jsonPath("$.rst_content.food_details[0].sugar_alcohol_unit_cd").value("g"));
    }

    @Test
    public void 식품_평가하기_목록을_조회한다() throws Exception {
        // Given
        FoodRandomRequest cond = FoodRandomRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .foodCategoryNo(1L)
                .randNum(0)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.random.food")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2))
                .andExpect(jsonPath("$.rst_content[0].food_no").value(2))
                .andExpect(jsonPath("$.rst_content[0].score").value(4))
                .andExpect(jsonPath("$.rst_content[1].food_no").value(1))
                .andExpect(jsonPath("$.rst_content[1].score").value(5));
    }

}
