package com.benope.apple.api.term.controller;

import com.benope.apple.api.AppleTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("MemberTermAgreeControllerTest.sql")
public class MemberTermAgreeControllerTest extends AppleTest {

    @Test
    public void 약관_동의_여부를_조회한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member.term.agree")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(Map.of("mb_no", 1L)))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(4))
                .andExpect(jsonPath("$.rst_content[0].term_version").value("0.2"))
                .andExpect(jsonPath("$.rst_content[1].term_version").value("0.2"))
                .andExpect(jsonPath("$.rst_content[2].term_version").value("0.2"))
                .andExpect(jsonPath("$.rst_content[3].term_version").value("0.2"));
    }

    @Test
    public void 약관_동의_여부를_등록한다_중복() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "mb_no", 1,
                "term_cd", "TERM01",
                "term_version", "0.1"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/agree.member.term")
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
    public void 약관_동의_여부를_등록한다_신규() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "mb_no", 1,
                "term_cd", "TERM01",
                "term_version", "0.3"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/agree.member.term")
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
    public void 약관_동의_여부를_취소한다_중복() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "mb_no", 1,
                "term_cd", "TERM01",
                "term_version", "0.1",
                "agree", "false"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/disagree.member.term")
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
    public void 약관_동의_여부를_취소한다_신규() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "mb_no", 1,
                "term_cd", "TERM01",
                "term_version", "0.3",
                "agree", false
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/disagree.member.term")
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
