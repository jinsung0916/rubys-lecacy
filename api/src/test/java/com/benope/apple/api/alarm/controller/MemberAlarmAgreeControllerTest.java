package com.benope.apple.api.alarm.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.alarm.bean.MemberAlarmAgreeRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("MemberAlarmAgreeControllerTest.sql")
public class MemberAlarmAgreeControllerTest extends AppleTest {

    @Test
    public void 알림_동의_여부를_조회한다() throws Exception {
        // Given
        MemberAlarmAgreeRequest cond = MemberAlarmAgreeRequest.builder()
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.alarm.agree")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(4));
    }

    @Test
    public void 동의_이력이_없는_경우_알림_동의_여부를_조회한다() throws Exception {
        // Given
        MemberAlarmAgreeRequest cond = MemberAlarmAgreeRequest.builder()
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.alarm.agree")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(4));
    }


    @Test
    public void 알림_동의_여부를_등록한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "mb_no", 1,
                "alarm_agree_cd", "ALARM01"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/agree.member.alarm")
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

    @Test
    public void 알림_동의_여부를_취소한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "mb_no", 1,
                "alarm_agree_cd", "ALARM01"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/disagree.member.alarm")
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
