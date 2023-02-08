package com.benope.apple.admin.appVersion.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.admin.appVersion.bean.AppVersionRequestV2;
import com.benope.apple.domain.appVersion.bean.AppVersion;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("AppVersionControllerV2Test.sql")
public class AppVersionControllerV2Test extends AppleAdminTest {

    @Test
    void 앱_버전_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "app_version", "IOS"
        );

        List<Integer> range = List.of(0, 10);

        List<String> sort = List.of("version", "ASC");

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/appVersion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .param("filter", mapper.writeValueAsString(cond))
                                .param("range", mapper.writeValueAsString(range))
                                .param("sort", mapper.writeValueAsString(sort))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$[0].version").value("1.0.0"))
                .andExpect(jsonPath("$[1].version").value("1.0.1"))
                .andExpect(jsonPath("$[2].version").value("1.0.2"));
    }

    @Test
    void ID로_카테고리_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "id", List.of(1, 2, 3)
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/appVersion")
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

    @Test
    void 버전을_생성한다() throws Exception {
        // Given
        AppVersionRequestV2 cond = AppVersionRequestV2.builder()
                .os(AppVersion.OS.IOS)
                .version("1.0.3")
                .forceUpdate(true)
                .memo("For test")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/appVersion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.os").value("IOS"))
                .andExpect(jsonPath("$.version").value("1.0.3"))
                .andExpect(jsonPath("$.force_update").value(true))
                .andExpect(jsonPath("$.memo").value("For test"));
    }

    @Test
    void 버전을_수정한다() throws Exception {
        // Given
        AppVersionRequestV2 cond = AppVersionRequestV2.builder()
                .os(AppVersion.OS.IOS)
                .version("1.0.3")
                .forceUpdate(true)
                .memo("For test")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/appVersion/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .header("X-API-VERSION", 2)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.os").value("IOS"))
                .andExpect(jsonPath("$.version").value("1.0.3"))
                .andExpect(jsonPath("$.force_update").value(true))
                .andExpect(jsonPath("$.memo").value("For test"));
    }

    @Test
    void 버전을_삭제한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/appVersion/1")
                                .contentType(MediaType.APPLICATION_JSON)
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