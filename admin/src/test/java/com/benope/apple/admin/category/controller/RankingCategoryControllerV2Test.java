package com.benope.apple.admin.category.controller;

import com.benope.apple.AppleAdminTest;
import com.benope.apple.admin.category.bean.RankingCategoryRequestV2;
import com.benope.apple.domain.category.bean.RankingConditionTypeCd;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("CategoryControllerTest.sql")
class RankingCategoryControllerV2Test extends AppleAdminTest {

    @Test
    void 랭킹_카테고리를_생성한다() throws Exception {
        // Given
        RankingCategoryRequestV2 cond = RankingCategoryRequestV2.builder()
                .categoryNm("created")
                .iconImgNo(1L)
                .rankingConditions(List.of(
                        RankingCategoryRequestV2.RankingConditionRequest.builder()
                                .rankingConditionTypeCd(RankingConditionTypeCd.MAIN)
                                .categoryNo(1L)
                                .build(),
                        RankingCategoryRequestV2.RankingConditionRequest.builder()
                                .rankingConditionTypeCd(RankingConditionTypeCd.SUB)
                                .categoryNo(2L)
                                .build()
                ))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/category/ranking")
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
                .andExpect(jsonPath("$.category_nm").value("created"))
                .andExpect(jsonPath("$.parent_category_no").value(0))
                .andExpect(jsonPath("$.category_type_cd").value("RANKING"))
                .andExpect(jsonPath("$.icon_img.img_no").value(1))
                .andExpect(jsonPath("$.ranking_conditions[0].ranking_condition_type_cd").value("MAIN"))
                .andExpect(jsonPath("$.ranking_conditions[0].category_no").value(1))
                .andExpect(jsonPath("$.ranking_conditions[1].ranking_condition_type_cd").value("SUB"))
                .andExpect(jsonPath("$.ranking_conditions[1].category_no").value(2));
    }

    @Test
    void 랭킹_카테고리를_수정한다() throws Exception {
        // Given
        RankingCategoryRequestV2 cond = RankingCategoryRequestV2.builder()
                .categoryNm("updated")
                .iconImgNo(1L)
                .rankingConditions(List.of(
                        RankingCategoryRequestV2.RankingConditionRequest.builder()
                                .rankingConditionTypeCd(RankingConditionTypeCd.MAIN)
                                .categoryNo(1L)
                                .build(),
                        RankingCategoryRequestV2.RankingConditionRequest.builder()
                                .rankingConditionTypeCd(RankingConditionTypeCd.SUB)
                                .categoryNo(2L)
                                .build()
                ))
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        put("/category/ranking/5")
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
                .andExpect(jsonPath("$.category_nm").value("updated"))
                .andExpect(jsonPath("$.parent_category_no").value(0))
                .andExpect(jsonPath("$.category_type_cd").value("RANKING"))
                .andExpect(jsonPath("$.icon_img.img_no").value(1))
                .andExpect(jsonPath("$.ranking_conditions[0].ranking_condition_type_cd").value("MAIN"))
                .andExpect(jsonPath("$.ranking_conditions[0].category_no").value(1))
                .andExpect(jsonPath("$.ranking_conditions[1].ranking_condition_type_cd").value("SUB"))
                .andExpect(jsonPath("$.ranking_conditions[1].category_no").value(2));
    }

}