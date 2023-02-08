package com.benope.apple.admin.banner.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.admin.banner.bean.BannerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("BannerControllerTest.sql")
public class BannerControllerTest extends AppleAdminTest {

    @Test
    void 배너_목록을_조회한다() throws Exception {
        // Given
        List<Integer> range = List.of(0, 10);

        List<String> sort = List.of("reorder_no", "ASC");

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/banner")
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
    void ID로_배너_목록을_조회한다() throws Exception {
        // Given
        Map<String, Object> cond = Map.of(
                "id", List.of(1, 2, 3)
        );

        List<Integer> range = List.of(0, 10);

        // When
        ResultActions resultActions = mockMvc.perform(
                        get("/banner")
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
    void 배너를_생성한다() throws Exception {
        // Given
        BannerRequest cond = BannerRequest.builder()
                .reorderNo(1)
                .bannerImgNo(1L)
                .linkUrl("https://www.rubys.io")
                .bannerNm("배너명")
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/banner")
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
                .andExpect(jsonPath("$.reorder_no").value(1))
                .andExpect(jsonPath("$.banner_img.img_no").value(1))
                .andExpect(jsonPath("$.link_url").value("https://www.rubys.io"))
                .andExpect(jsonPath("$.banner_nm").value("배너명"))
                .andExpect(jsonPath("$.start_date_time").isNotEmpty())
                .andExpect(jsonPath("$.end_date_time").isNotEmpty());
    }

    @Test
    void 버전을_수정한다() throws Exception {
        // Given
        BannerRequest cond = BannerRequest.builder()
                .reorderNo(1)
                .bannerImgNo(1L)
                .linkUrl("https://www.rubys.io")
                .bannerNm("배너명")
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/banner/1")
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
                .andExpect(jsonPath("$.reorder_no").value(1))
                .andExpect(jsonPath("$.banner_img.img_no").value(1))
                .andExpect(jsonPath("$.link_url").value("https://www.rubys.io"))
                .andExpect(jsonPath("$.banner_nm").value("배너명"))
                .andExpect(jsonPath("$.start_date_time").isNotEmpty())
                .andExpect(jsonPath("$.end_date_time").isNotEmpty());
    }

    @Test
    void 버전을_삭제한다() throws Exception {
        // When
        ResultActions resultActions = mockMvc.perform(
                        delete("/banner/1")
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