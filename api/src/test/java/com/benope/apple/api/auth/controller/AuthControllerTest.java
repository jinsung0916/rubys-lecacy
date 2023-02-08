package com.benope.apple.api.auth.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.auth.bean.FcmTokenRequest;
import com.benope.apple.api.auth.bean.JoinRequest;
import com.benope.apple.api.auth.bean.LoginRequest;
import com.benope.apple.api.auth.service.LoginHistoryService;
import com.benope.apple.api.member.bean.MemberRequest;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.member.bean.MemberAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("AuthControllerTest.sql")
public class AuthControllerTest extends AppleTest {

    @Autowired
    private LoginHistoryService loginHistoryService;

    @Test
    public void 로그인을_처리한다() throws Exception {
        // Given
        LoginRequest.UserInput cond = LoginRequest.UserInput.builder()
                .type(MemberAuth.MbAuthCd.APPLE)
                .idToken("12345")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.login")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.access_token").isNotEmpty())
                .andExpect(jsonPath("$.rst_content.refresh_token").isNotEmpty());
    }

    @Test
    public void 존재하지_않은_회원정보로_로그인_시도할_경우_400을_반환한다() throws Exception {
        // Given
        LoginRequest.UserInput cond = LoginRequest.UserInput.builder()
                .type(MemberAuth.MbAuthCd.NAVER)
                .idToken("12345")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.login")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.MEMBER_NOT_EXISTS));
    }

    @Test
    public void 탈퇴회원_로그인_시_400_을_반환한다() throws Exception {
        // Given
        LoginRequest.UserInput cond = LoginRequest.UserInput.builder()
                .type(MemberAuth.MbAuthCd.GOOGLE)
                .idToken("12345")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.login")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.QUIT_MEMBER));
    }

    @Test
    public void 회원가입을_처리한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "type", "NAVER",
                "id_token", "12345",
                "email", "jinsung0916@naver.com",
                "nickname", "AJ",
                "profile_img_no", 1,
                "term_agree_list", List.of(
                        Map.of("term_cd", "TERM01", "term_version", "0.1", "agree", "true"),
                        Map.of("term_cd", "TERM02", "term_version", "0.1", "agree", "true"),
                        Map.of("term_cd", "TERM03", "term_version", "0.1", "agree", "true"),
                        Map.of("term_cd", "TERM04", "term_version", "0.1", "agree", "true")
                )
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.join")
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
    public void 필수_약관에_동의하지_않을_경우_400을_반환한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "type", "NAVER",
                "id_token", "12345",
                "email", "jinsung0916@naver.com",
                "nickname", "AJ",
                "term_agree_list", List.of(
                        Map.of("term_cd", "TERM01", "term_version", "0.1", "agree", "false"),
                        Map.of("term_cd", "TERM02", "term_version", "0.1", "agree", "false"),
                        Map.of("term_cd", "TERM03", "term_version", "0.1", "agree", "false"),
                        Map.of("term_cd", "TERM04", "term_version", "0.1", "agree", "false")
                )
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.join")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.MANDATORY_TERM_DISAGREE));
    }

    @Test
    public void 중복된_닉네임을_확인한다() throws Exception {
        // Given
        MemberRequest cond = MemberRequest.builder()
                .nickname("newNickname")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.check.nickname")
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
    public void 회원_탈퇴를_처리한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.quit")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(print());


        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                        post("/get.member")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(Map.of("search_mb_no", 1)))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 회원_탈퇴_후_재가입을_방지한다() throws Exception {
        // Given
        JoinRequest cond = JoinRequest.builder()
                .type(MemberAuth.MbAuthCd.GOOGLE)
                .idToken("12345")
                .email("jinsung0916@naver.com")
                .nickname("AJ")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.join")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        //Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.QUIT_MEMBER));
    }

    @Test
    public void 중복된_닉네임을_요청할_경우_400을_반환한다() throws Exception {
        // Given
        MemberRequest cond = MemberRequest.builder()
                .nickname("duplicatedNickname")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.check.nickname")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.DUPLICATED_NICKNAME));
    }

    @Test
    public void 토큰을_갱신한다() throws Exception {
        // Given
        String refreshToken = refreshToken(1L);

        loginHistoryService.update(
                LoginHistory.builder()
                        .loginHistoryNo(1L)
                        .refreshToken(refreshToken)
                        .build()
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.refresh.token")
                                .header("Authorization", "Bearer " + refreshToken)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.access_token").isNotEmpty());
    }

    @Test
    public void FCM_토큰을_등록한다() throws Exception {
        // Given
        FcmTokenRequest cond = FcmTokenRequest.builder()
                .fcmToken("cKOSSJFtTiGMOtNAmMXthG:APA91bHjtLRezXtpuZfSVEsF3gDPynv07dl8TuRa02aIQlPXvgo7kXyYvRFuwlBZGzFi1Ks20-2TpEihZE-Dz4tRt7EU-yiU2z-9bwj9eZVYdF5kEtzr68MTcLpFVz6dSuWDnipW5UWP")
                .build();

        String refreshToken = refreshToken(1L);

        loginHistoryService.update(
                LoginHistory.builder()
                        .loginHistoryNo(1L)
                        .refreshToken(refreshToken)
                        .build()
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.reg.fcm.token")
                                .header("Authorization", "Bearer " + refreshToken)
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
    public void 로그아웃을_처리한다() throws Exception {
        // Given
        String refreshToken = refreshToken(1L);

        loginHistoryService.update(
                LoginHistory.builder()
                        .loginHistoryNo(1L)
                        .refreshToken(refreshToken)
                        .build()
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/auth.logout")
                                .header("Authorization", "Bearer " + refreshToken)
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                        post("/auth.refresh.token")
                                .header("Authorization", "Bearer " + refreshToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(status().is4xxClientError());
    }
}
