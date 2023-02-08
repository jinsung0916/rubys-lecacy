package com.benope.apple.admin.report.controller;

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

@Sql("FoodReportControllerTest.sql")
public class FoodReportControllerV2Test extends AppleAdminTest {

    @Test
    public void 제품요청을_ID_로_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "id", List.of(1, 2, 3)
        );

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/report/food")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(3));
    }


}
