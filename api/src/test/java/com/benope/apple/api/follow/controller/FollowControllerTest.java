package com.benope.apple.api.follow.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.alarm.bean.AlarmRequest;
import com.benope.apple.api.follow.bean.FollowRequest;
import com.benope.apple.api.follow.bean.FollowSearchRequest;
import com.benope.apple.api.member.bean.MemberSearchRequest;
import com.benope.apple.config.webMvc.RstCode;
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

@Sql("FollowControllerTest.sql")
public class FollowControllerTest extends AppleTest {

    @Autowired
    private EntityManager em;

    @Test
    public void 다른_회원을_팔로우한다() throws Exception {
        // Given
        FollowRequest cond = FollowRequest.builder()
                .followMbNo(10L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.member.follow")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.mb_no").value(10));
    }

    @Test
    public void 다른_회원을_팔로우할_때_팔로우_팔로잉_횟수를_증가시킨다() throws Exception {
        // Given
        FollowRequest cond = FollowRequest.builder()
                .followMbNo(10L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.member.follow")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.mb_no").value(10));

        mockMvc.perform(
                        post("/get.member")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(
                                        MemberSearchRequest.builder().searchMbNo(1L).build()
                                ))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.following_cnt").value(1));

        mockMvc.perform(
                        post("/get.member")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(
                                        MemberSearchRequest.builder().searchMbNo(10L).build()
                                ))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.follower_cnt").value(1));
    }

    @Test
    public void 내가_팔로우한_회원을_조회한다() throws Exception {
        // Given
        FollowSearchRequest cond = FollowSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.following")
                                .header("Authorization", "Bearer " + accessToken(10L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());
        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].mb_no").value(1));
    }

    @Test
    public void 나를_팔로우한_회원을_조회한다() throws Exception {
        // Given
        FollowSearchRequest cond = FollowSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.follower")
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
                .andExpect(jsonPath("$.rst_content[0].mb_no").value(10));
    }

    @Test
    public void 탈퇴한_회원을_팔로잉_목록에서_제외한다() throws Exception {
        // Given
        FollowSearchRequest cond = FollowSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.following")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());
        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(0));
    }

    @Test
    public void 탈퇴한_회원을_팔로워_목록에서_제외한다() throws Exception {
        // Given
        FollowSearchRequest cond = FollowSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.follower")
                                .header("Authorization", "Bearer " + accessToken(10L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());
        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(0));
    }

    @Test
    public void 팔로우를_취소한다() throws Exception {
        // Given
        FollowRequest cond = FollowRequest.builder()
                .followMbNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.member.follow")
                                .header("Authorization", "Bearer " + accessToken(10L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.mb_no").value(1L));

        FollowRequest check = FollowRequest.builder()
                .followMbNo(1L)
                .build();

        mockMvc.perform(
                        post("/reg.member.follow")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void 팔로우를_취소할_때_팔로우_팔로잉_횟수를_감소시킨다() throws Exception {
        // Given
        FollowRequest cond = FollowRequest.builder()
                .followMbNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.member.follow")
                                .header("Authorization", "Bearer " + accessToken(10L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.mb_no").value(1L));

        mockMvc.perform(
                        post("/get.member")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(
                                        MemberSearchRequest.builder().searchMbNo(1L).build()
                                ))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.following_cnt").value(0));

        mockMvc.perform(
                        post("/get.member")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(
                                        MemberSearchRequest.builder().searchMbNo(1L).build()
                                ))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.follower_cnt").value(0));
    }

    @Test
    public void 팔로우_하지_않은_회원을_취소할_경우_400을_반환한다() throws Exception {
        // Given
        FollowRequest cond = FollowRequest.builder()
                .followMbNo(10L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.member.follow")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.NOT_FOUND));
    }

    @Test
    public void 팔로우_시_알림을_전송한다() throws Exception {
        // Given
        FollowRequest cond = FollowRequest.builder()
                .followMbNo(10L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.member.follow")
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
                                .header("Authorization", "Bearer " + accessToken(10L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].alarm_target_cd").value("MEMBER"))
                .andExpect(jsonPath("$.rst_content[0].object_no").value(1))
                .andExpect(jsonPath("$.rst_content[0].content").value("<b>AJ</b>님이 회원님을 팔로우하기 시작했습니다."))
                .andExpect(jsonPath("$.rst_content[0].read").value(false));
    }

}
