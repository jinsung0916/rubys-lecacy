package com.benope.apple.admin.score.controller;

import com.benope.apple.AppleAdminTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ScoreControllerTest.sql")
public class ScoreControllerTest extends AppleAdminTest {

    @Test
    void 스코어_목록을_조회한다() throws Exception {
        // Given
        List<Integer> range = List.of(0, 10);

        List<String> sort = List.of("created_at_or_updated_at", "DESC");

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/score")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("range", mapper.writeValueAsString(range))
                                .param("sort", mapper.writeValueAsString(sort))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(1));
    }

    @Test
    void ID로_스코어_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "id", List.of(1, 2, 3)
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/score")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(3));
    }

}
