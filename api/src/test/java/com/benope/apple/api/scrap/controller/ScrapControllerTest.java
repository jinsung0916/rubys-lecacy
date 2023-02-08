package com.benope.apple.api.scrap.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.feed.bean.FeedRequest;
import com.benope.apple.api.scrap.bean.ScrapRequest;
import com.benope.apple.api.scrap.bean.ScrapRequestComposite;
import com.benope.apple.api.scrap.bean.ScrapSearchRequest;
import com.benope.apple.domain.scrap.bean.ScrapTypeCd;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ScrapControllerTest.sql")
class ScrapControllerTest extends AppleTest {

    @Test
    public void 스크랩과_디렉토리_리스트를_불러온다() throws Exception {
        // Given
        ScrapSearchRequest cond = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3))
                .andExpect(jsonPath("$.rst_content[0].directory_no").value(2))
                .andExpect(jsonPath("$.rst_content[0].type").value("DIRECTORY"))
                .andExpect(jsonPath("$.rst_content[0].feed").isNotEmpty())
                .andExpect(jsonPath("$.rst_content[1].scrap_no").value(4))
                .andExpect(jsonPath("$.rst_content[1].type").value("SCRAP"))
                .andExpect(jsonPath("$.rst_content[1].feed").isNotEmpty())
                .andExpect(jsonPath("$.rst_content[2].scrap_no").value(1))
                .andExpect(jsonPath("$.rst_content[2].type").value("SCRAP"))
                .andExpect(jsonPath("$.rst_content[2].feed").isNotEmpty());
    }

    @Test
    public void 스크랩을_별도로_조회한다() throws Exception {
        // Given
        ScrapSearchRequest cond = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .type(ScrapTypeCd.SCRAP)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2))
                .andExpect(jsonPath("$.rst_content[0].type").value("SCRAP"))
                .andExpect(jsonPath("$.rst_content[1].type").value("SCRAP"));
    }

    @Test
    public void 디렉토리를_별도로_조회한다() throws Exception {
        // Given
        ScrapSearchRequest cond = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .type(ScrapTypeCd.DIRECTORY)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].type").value("DIRECTORY"));
    }

    @Test
    public void 디렉토리_내부의_스크랩과_디렉토리_리스트를_불러온다() throws Exception {
        // Given
        ScrapSearchRequest cond = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .directoryNo(2L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(1))
                .andExpect(jsonPath("$.rst_content[0].scrap_no").value(3))
                .andExpect(jsonPath("$.rst_content[0].type").value("SCRAP"));
    }

    @Test
    public void 스크랩을_등록한다() throws Exception {
        // Given
        ScrapRequest cond = ScrapRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.scrap")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.feed.feed_no").value(1));
    }

    @Test
    public void 스크랩을_변경한다_IN() throws Exception {
        // Given
        ScrapRequest cond = ScrapRequest.builder()
                .scrapNo(1L)
                .directoryNo(2L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Given
        resultActions
                .andExpect(status().is2xxSuccessful());

        ScrapSearchRequest check = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .directoryNo(2L)
                .build();

        mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2));
    }

    @Test
    public void 스크랩을_변경한다_OUT() throws Exception {
        // Given
        ScrapRequest cond = ScrapRequest.builder()
                .scrapNo(3L)
                .directoryNo(0L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        ScrapSearchRequest check = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(4));
    }

    @Test
    public void 다수의_스크랩을_변경한다() throws Exception {
        // Given
        ScrapRequestComposite cond = ScrapRequestComposite.builder()
                .scrapNo(List.of(1L, 4L))
                .directoryNo(2L)
                .build();

        // When
        mockMvc.perform(
                        post("/mod.scrap.multiple")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andExpect(status().is2xxSuccessful());

        // Then
        ScrapSearchRequest check = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .directoryNo(2L)
                .build();

        mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(3));
    }

    @Test
    public void 스크랩을_삭제한다() throws Exception {
        // Given
        ScrapRequest cond = ScrapRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        ScrapRequest check = ScrapRequest.builder()
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/reg.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void 스크랩_시_피드_스크랩_수를_증가시킨다() throws Exception {
        // Given
        ScrapRequest cond = ScrapRequest.builder()
                .feedNo(1L)
                .build();


        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.scrap")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        FeedRequest check = FeedRequest.builder()
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.feed.detail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.scrap_cnt").value(2));
    }

    @Test
    public void 스크랩_취소_시_피드_스크랩_수를_감소시킨다() throws Exception {
        // Given
        ScrapRequest cond = ScrapRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        FeedRequest check = FeedRequest.builder()
                .feedNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.feed.detail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.scrap_cnt").value(0));
    }

    @Test
    public void 피드_삭제_시_스크랩을_조회_결과에서_제외한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.feed")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        ScrapSearchRequest check = ScrapSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(10)
                .build();

        mockMvc.perform(
                        post("/get.scrap")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.size()").value(2));
    }
}