package com.benope.apple.admin.member.controller;

import com.benope.apple.AppleAdminTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql
public class MemberControllerV2Test extends AppleAdminTest {

    @Test
    public void 회원을_ID_로_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "id", List.of(1, 2, 3, 4)
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].mb_auth_cd").value("GOOGLE"))
                .andExpect(jsonPath("$[1].mb_auth_cd").value("APPLE"))
                .andExpect(jsonPath("$[2].mb_auth_cd").value("NAVER"))
                .andExpect(jsonPath("$[3].mb_auth_cd").value("KAKAO"));
    }

    @Test
    public void 회원_상세를_조회한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/member/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.nickname").value("AJ"))
                .andExpect(jsonPath("$.last_access_at").isNotEmpty());
    }

    @Test
    public void 회원을_갱신한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "expert_cd", "PRO"
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/member/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.expert_cd").value("PRO"));
    }

}