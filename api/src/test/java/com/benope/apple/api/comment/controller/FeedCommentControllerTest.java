package com.benope.apple.api.comment.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.alarm.bean.AlarmRequest;
import com.benope.apple.api.comment.bean.FeedCommentRequest;
import com.benope.apple.api.comment.bean.FeedCommentSearchRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FeedCommentControllerTest.sql")
public class FeedCommentControllerTest extends AppleTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 피드_댓글을_조회한다() throws Exception {
        // Given
        FeedCommentSearchRequest cond = FeedCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.feed.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].comment_no").value(1));
    }

    @Test
    public void 피드_서브_댓글을_조회한다() throws Exception {
        // Given
        FeedCommentSearchRequest cond = FeedCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .feedNo(1L)
                .parentCommentNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.feed.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].comment_no").value(2));
    }

    @Test
    public void 댓글을_수정한다() throws Exception {
        // Given
        FeedCommentRequest cond = FeedCommentRequest.builder()
                .commentNo(2L)
                .contents("변경된 내용")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.feed.comment")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.parent_comment_no").value(1))
                .andExpect(jsonPath("$.rst_content.contents").value("변경된 내용"));
    }

    @Test
    public void 답글_작성_시_댓글_답글_수를_증가시킨다() throws Exception {
        // Given
        FeedCommentRequest cond = FeedCommentRequest.builder()
                .feedNo(1L)
                .parentCommentNo(1L)
                .contents("created")
                .depth((byte) 1)
                .reorderNo(0)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed.comment")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        em.clear();

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        FeedCommentSearchRequest check = FeedCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(1)
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.feed.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content[0].sub_comment_cnt").value(2));
    }

    @Test
    public void 답글_작성_시_댓글_답글_수를_감소시킨다() throws Exception {
        // Given
        FeedCommentRequest cond = FeedCommentRequest.builder()
                .commentNo(2L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.feed.comment")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        FeedCommentSearchRequest check = FeedCommentSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(1)
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.feed.comment")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content[0].sub_comment_cnt").value(0));
    }

    @Test
    public void 피드_댓글_등록_시_알림을_전송한다() throws Exception {
        // Given
        FeedCommentRequest cond = FeedCommentRequest.builder()
                .feedNo(1L)
                .contents("created")
                .depth(Byte.valueOf("0"))
                .reorderNo(0)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed.comment")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        em.clear();

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        AlarmRequest check = AlarmRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(1)
                .build();

        mockMvc.perform(
                        post("/get.alarm")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].alarm_target_cd").value("FEED"))
                .andExpect(jsonPath("$.rst_content[0].object_no").value(1))
                .andExpect(jsonPath("$.rst_content[0].content").value("<b>AJ</b>님이 댓글을 남겼습니다: created"))
                .andExpect(jsonPath("$.rst_content[0].read").value(false));
    }

    @Test
    public void 피드_답글_등록_시_알림을_전송한다() throws Exception {
        // Given
        FeedCommentRequest cond = FeedCommentRequest.builder()
                .feedNo(1L)
                .parentCommentNo(1L)
                .contents("created")
                .depth(Byte.valueOf("1"))
                .reorderNo(0)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed.comment")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        em.clear();

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        AlarmRequest check = AlarmRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(1)
                .build();

        mockMvc.perform(
                        post("/get.alarm")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].alarm_target_cd").value("FEED"))
                .andExpect(jsonPath("$.rst_content[0].object_no").value(1))
                .andExpect(jsonPath("$.rst_content[0].content").value("<b>AJ</b>님이 회원님의 댓글에 답글을 남겼습니다: created"))
                .andExpect(jsonPath("$.rst_content[0].read").value(false));
    }
}
