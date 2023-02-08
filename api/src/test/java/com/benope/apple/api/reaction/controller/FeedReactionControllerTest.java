package com.benope.apple.api.reaction.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.alarm.bean.AlarmRequest;
import com.benope.apple.api.feed.bean.FeedRequest;
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

@Sql("FeedReactionControllerTest.sql")
public class FeedReactionControllerTest extends AppleTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 피드_좋아요를_처리한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/chk.feed.favorite")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        em.clear();

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.like").value(true))
                .andExpect(jsonPath("$.rst_content.feed_no").value(1));

        FeedRequest check = FeedRequest.builder()
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.feed.detail")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.like").value(true))
                .andExpect(jsonPath("$.rst_content.like_cnt").value(2));
    }

    @Test
    public void 피드_좋아요를_취소한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/chk.feed.favorite")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.like").value(false))
                .andExpect(jsonPath("$.rst_content.feed_no").value(1));


        FeedRequest check = FeedRequest.builder()
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.feed.detail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.like").value(false))
                .andExpect(jsonPath("$.rst_content.like_cnt").value(0));
    }

    @Test
    public void 피드_좋아요_시_알림을_전송한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/chk.feed.favorite")
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
                .andExpect(jsonPath("$.rst_content[0].content").value("<b>AJ</b>님이 회원님의 게시글을 좋아합니다."))
                .andExpect(jsonPath("$.rst_content[0].read").value(false));
    }


}
