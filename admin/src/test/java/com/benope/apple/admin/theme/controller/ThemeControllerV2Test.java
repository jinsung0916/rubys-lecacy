package com.benope.apple.admin.theme.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.admin.theme.bean.ThemeRequestV2;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.theme.bean.ThemeConditionCd;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ThemeControllerTest.sql")
public class ThemeControllerV2Test extends AppleAdminTest {

    @Test
    public void 테마_목록을_조회한다() throws Exception {
        // Given
        List<Integer> range = List.of(0, 10);

        // Then
        ResultActions resultActions = mockMvc.perform(
                        get("/theme")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("range", mapper.writeValueAsString(range))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(10));
    }

    @Test
    public void 테마를_등록한다() throws Exception {
        // Given
        ThemeRequestV2 cond = ThemeRequestV2.builder()
                .themeNm("테마")
                .displayNm("테마 전시명")
                .reorderNo(1L)
                .picked(false)
                .displayAll(false)
                .conditions(List.of(
                        ThemeRequestV2.ThemeConditionRequest.builder()
                                .themeConditionCd(ThemeConditionCd.EXPERT)
                                .expertCd(ExpertCd.PRO)
                                .batchSize(5)
                                .build()
                ))
                .build();

        // Then
        ResultActions resultActions = mockMvc.perform(
                        post("/theme")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.theme_nm").value("테마"))
                .andExpect(jsonPath("$.display_nm").value("테마 전시명"))
                .andExpect(jsonPath("$.reorder_no").value(1))
                .andExpect(jsonPath("$.conditions.size()").value(1))
                .andExpect(jsonPath("$.conditions[0].theme_condition_cd").value("EXPERT"))
                .andExpect(jsonPath("$.conditions[0].expert_cd").value("PRO"))
                .andExpect(jsonPath("$.conditions[0].batch_size").value(5));
    }

    @Test
    public void 테마를_변경한다() throws Exception {
        // Given
        ThemeRequestV2 cond = ThemeRequestV2.builder()
                .themeNm("테마")
                .displayNm("테마 전시명")
                .reorderNo(1L)
                .conditions(List.of(
                        ThemeRequestV2.ThemeConditionRequest.builder()
                                .themeConditionCd(ThemeConditionCd.EXPERT)
                                .expertCd(ExpertCd.PRO)
                                .batchSize(5)
                                .build()
                ))
                .build();

        // Then
        ResultActions resultActions = mockMvc.perform(
                        put("/theme/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.theme_nm").value("테마"))
                .andExpect(jsonPath("$.display_nm").value("테마 전시명"))
                .andExpect(jsonPath("$.reorder_no").value(1))
                .andExpect(jsonPath("$.conditions.size()").value(1))
                .andExpect(jsonPath("$.conditions[0].theme_condition_cd").value("EXPERT"))
                .andExpect(jsonPath("$.conditions[0].expert_cd").value("PRO"))
                .andExpect(jsonPath("$.conditions[0].batch_size").value(5));
    }

    @Test
    public void 테마를_삭제한다() throws Exception {
        // Then
        ResultActions resultActions = mockMvc.perform(
                        delete("/theme/1")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").value(1));
    }

}
