package com.benope.apple.api.score.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.score.bean.FoodScoreRequest;
import com.benope.apple.api.score.bean.FoodScoreSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodScoreControllerTest.sql")
public class FoodScoreControllerTest extends AppleTest {

    @Test
    public void 식품_스코어를_조회한다_회원() throws Exception {
        // Given
        FoodScoreSearchRequest cond = FoodScoreSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.score")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3))
                .andExpect(jsonPath("$.rst_content[0].score_no").value(3))
                .andExpect(jsonPath("$.rst_content[0].following").value(false))
                .andExpect(jsonPath("$.rst_content[0].mb_no").value(3))
                .andExpect(jsonPath("$.rst_content[1].score_no").value(2))
                .andExpect(jsonPath("$.rst_content[1].following").value(true))
                .andExpect(jsonPath("$.rst_content[1].mb_no").value(2))
                .andExpect(jsonPath("$.rst_content[2].score_no").value(1))
                .andExpect(jsonPath("$.rst_content[2].following").value(false))
                .andExpect(jsonPath("$.rst_content[2].mb_no").value(1))
                .andExpect(jsonPath("$.rst_content[2].expert_cd.code").value("PRO"))
                .andExpect(jsonPath("$.rst_content[2].expert_cd.desc").value("프로식단러"));
    }

    @Test
    public void 식품_스코어를_조회한다_비회원() throws Exception {
        // Given
        FoodScoreSearchRequest cond = FoodScoreSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.score")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void 특정_회원_식품_스코어를_조회한다() throws Exception {
        // Given
        FoodScoreSearchRequest cond = FoodScoreSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .searchMbNo(3L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.score")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].score_no").value(3))
                .andExpect(jsonPath("$.rst_content[0].following").value(false));
    }

    @Test
    public void 팔로워_식품_스코어를_조회한다() throws Exception {
        // Given
        FoodScoreSearchRequest cond = FoodScoreSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.following.food.score")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].score_no").value(2))
                .andExpect(jsonPath("$.rst_content[0].following").value(true))
                .andExpect(jsonPath("$.rst_content[0].mb_no").isNotEmpty());
    }

    @Test
    public void 식품_스코어_요약을_조회한다_회원() throws Exception {
        // Given
        FoodScoreRequest cond = FoodScoreRequest.builder()
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.score.summary")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.food_no").value(1))
                .andExpect(jsonPath("$.rst_content.score").value(4.2))
                .andExpect(jsonPath("$.rst_content.score_count").value(3))
                .andExpect(jsonPath("$.rst_content.my_score").value(4));
    }

    @Test
    public void 식품_스코어_요약을_조회한다_비회원() throws Exception {
        // Given
        FoodScoreRequest cond = FoodScoreRequest.builder()
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.score.summary")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.food_no").value(1))
                .andExpect(jsonPath("$.rst_content.score").value(4.2))
                .andExpect(jsonPath("$.rst_content.score_count").value(3))
                .andExpect(jsonPath("$.rst_content.my_score").isEmpty());
    }

    @Test
    public void 식품_스코어를_등록한다() throws Exception {
        // Given
        FoodScoreRequest cond = FoodScoreRequest.builder()
                .foodNo(1L)
                .score(BigDecimal.valueOf(0.5))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/score.food")
                                .header("Authorization", "Bearer " + accessToken(5L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.food_no").value(1))
                .andExpect(jsonPath("$.rst_content.score").value(0.5));

        mockMvc.perform(
                        post("/get.food.score.summary")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andExpect(jsonPath("$.rst_content.score").value(3.3))
                .andExpect(jsonPath("$.rst_content.score_count").value(4));
    }

    @Test
    public void 식품_스코어를_갱신한다() throws Exception {
        // Given
        FoodScoreRequest cond = FoodScoreRequest.builder()
                .foodNo(1L)
                .score(BigDecimal.valueOf(5))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/score.food")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.food_no").value(1))
                .andExpect(jsonPath("$.rst_content.score").value(5));

        mockMvc.perform(
                        post("/get.food.score.summary")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andExpect(jsonPath("$.rst_content.score").value(4.5))
                .andExpect(jsonPath("$.rst_content.score_count").value(3));
    }

    @Test
    public void 식품_스코어를_취소한다() throws Exception {
        // Given
        FoodScoreRequest cond = FoodScoreRequest.builder()
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/unscore.food")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.food_no").value(1));

        mockMvc.perform(
                        post("/get.food.score.summary")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andExpect(jsonPath("$.rst_content.score").value(4.3))
                .andExpect(jsonPath("$.rst_content.score_count").value(2));
    }

}
