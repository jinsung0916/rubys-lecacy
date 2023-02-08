package com.benope.apple.admin.theme.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.admin.theme.bean.ThemeFeedRequestV2;
import com.benope.apple.config.webMvc.RstCode;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ThemeFeedControllerTest.sql")
public class ThemeFeedControllerV2Test extends AppleAdminTest {

    @Test
    public void 피드_테마를_조회한다() throws Exception {
        // Given
        List<Integer> range = List.of(0, 10);

        List<String> sort = List.of("reorder_no", "ASC");

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/themeFeed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
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
                .andExpect(jsonPath("$[0].feed_no").value(1))
                .andExpect(jsonPath("$[1].feed_no").value(3))
                .andExpect(jsonPath("$[2].feed_no").value(2));
    }

    @Test
    public void 피드_테마_상세_를_조회한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/themeFeed/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void 피드_테마를_등록한다() throws Exception {
        // Given
        ThemeFeedRequestV2 cond = ThemeFeedRequestV2.builder()
                .themeNo(2L)
                .feedNo(6L)
                .reorderNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/themeFeed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.theme_no").value(2))
                .andExpect(jsonPath("$.reorder_no").value(1))
                .andExpect(jsonPath("$.feed_no").value(6));
    }

    @Test
    public void 이미_등록된_피드를_등록할_경우_400을_반환한다() throws Exception {
        // Given
        ThemeFeedRequestV2 cond = ThemeFeedRequestV2.builder()
                .themeNo(2L)
                .feedNo(1L)
                .reorderNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/themeFeed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(isBusinessException(RstCode.FEED_ALREADY_REGISTERED));
    }

    @Test
    public void 피드_테마를_수정한다() throws Exception {
        // Given
        ThemeFeedRequestV2 cond = ThemeFeedRequestV2.builder()
                .themeNo(1L)
                .feedNo(6L)
                .reorderNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/themeFeed/4")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.theme_no").value(1))
                .andExpect(jsonPath("$.reorder_no").value(1))
                .andExpect(jsonPath("$.feed_no").value(6));
    }

    @Test
    public void 피드_테마를_삭제한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/themeFeed/5")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value(5));
    }


}
