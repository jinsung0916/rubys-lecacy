package com.benope.apple.api.comment.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.comment.bean.FoodCommentRequest;
import com.benope.apple.api.comment.bean.FoodCommentSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FoodCommentControllerTest.sql")
public class FoodCommentControllerTest extends AppleTest {

    @Test
    public void 식품_댓글을_조회한다() throws Exception {
        // Given
        FoodCommentSearchRequest cond = FoodCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .foodNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2))
                .andExpect(jsonPath("$.rst_content[0].comment_no").value(2))
                .andExpect(jsonPath("$.rst_content[0].nickname").value("Hong"))
                .andExpect(jsonPath("$.rst_content[0].score").isEmpty())
                .andExpect(jsonPath("$.rst_content[1].comment_no").value(1))
                .andExpect(jsonPath("$.rst_content[1].nickname").value("AJ"))
                .andExpect(jsonPath("$.rst_content[1].score").value(5));
    }

    @Test
    public void 특정_회원의_식품_댓글을_조회한다() throws Exception {
        // Given
        FoodCommentSearchRequest cond = FoodCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .foodNo(1L)
                .searchMbNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].comment_no").value(1))
                .andExpect(jsonPath("$.rst_content[0].nickname").value("AJ"))
                .andExpect(jsonPath("$.rst_content[0].score").value(5));
    }

    @Test
    public void 특정_회원의_전체_식품_댓글을_조회한다() throws Exception {
        // Given
        FoodCommentSearchRequest cond = FoodCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .searchMbNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.food.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2))
                .andExpect(jsonPath("$.rst_content[0].comment_no").value(3))
                .andExpect(jsonPath("$.rst_content[0].nickname").value("AJ"))
                .andExpect(jsonPath("$.rst_content[0].score").isEmpty())
                .andExpect(jsonPath("$.rst_content[1].comment_no").value(1))
                .andExpect(jsonPath("$.rst_content[1].nickname").value("AJ"))
                .andExpect(jsonPath("$.rst_content[1].score").value(5));
    }

    @Test
    public void 식품_댓글을_등록한다() throws Exception {
        FoodCommentRequest cond = FoodCommentRequest.builder()
                .foodNo(1L)
                .contents("내용")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.food.comment")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.contents").value("내용"))
                .andExpect(jsonPath("$.rst_content.score").value(5.0));
    }

    @Test
    public void 식품_댓글을_수정한다() throws Exception {
        // Given
        FoodCommentRequest cond = FoodCommentRequest.builder()
                .commentNo(1L)
                .contents("변경된 내용")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.food.comment")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.contents").value("변경된 내용"))
                .andExpect(jsonPath("$.rst_content.score").value(5.0));
    }

    @Test
    public void 식품_댓글을_삭제한다() throws Exception {
        FoodCommentRequest cond = FoodCommentRequest.builder()
                .commentNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.food.comment")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());
    }

}
