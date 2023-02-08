package com.benope.apple.api.scrap.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.feed.bean.FeedRequest;
import com.benope.apple.api.scrap.bean.DirectoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("ScrapControllerTest.sql")
public class ScrapDirectoryControllerTest extends AppleTest {

    @Test
    public void 디렉토리를_생성한다() throws Exception {
        // Given
        DirectoryRequest cond = DirectoryRequest.builder()
                .directoryName("created")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.scrap.directory")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.directory_name").value("created"));
    }

    @Test
    public void 디렉토리를_변경한다() throws Exception {
        // Given
        DirectoryRequest cond = DirectoryRequest.builder()
                .directoryNo(2L)
                .directoryName("modified")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.scrap.directory")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.directory_name").value("modified"));
    }

    @Test
    public void 디렉토리를_삭제한다() throws Exception {
        // Given
        DirectoryRequest cond = DirectoryRequest.builder()
                .directoryNo(2L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.scrap.directory")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_cd").value(100))
                .andExpect(jsonPath("$.rst_content.count").value(2));
    }

    @Test
    public void 디렉토리_삭제_시_삭제되는_모든_스크랩의_피드_스크랩_수를_감소시킨다() throws Exception {
        // Given
        DirectoryRequest cond = DirectoryRequest.builder()
                .directoryNo(2L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                post("/del.scrap.directory")
                        .header("Authorization", "Bearer " + accessToken(1L))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(cond))
        )
                .andDo(print());

        resultActions
                .andExpect(status().is2xxSuccessful());

        FeedRequest check = FeedRequest.builder()
                .feedNo(2L)
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

}
