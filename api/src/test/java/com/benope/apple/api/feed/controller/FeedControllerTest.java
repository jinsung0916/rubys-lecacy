package com.benope.apple.api.feed.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.feed.bean.FeedDetailRequest;
import com.benope.apple.api.feed.bean.FeedRequest;
import com.benope.apple.api.feed.bean.FeedSearchRequest;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.repository.FoodJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("FeedControllerTest.sql")
class FeedControllerTest extends AppleTest {

    @Autowired
    private FoodJpaRepository foodJpaRepository;

    @Autowired
    private EntityManager em;

    @Test
    void 피드_목록을_조회한다_최신순() throws Exception {
        // Given
        FeedSearchRequest cond = FeedSearchRequest.builder()
                .currentPageNo(1)
                .recordsPerPage(1)
                .feedTypeCd(FeedTypeCd.FEED)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.feed")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content[0].feed_no").value(3));
    }

    @Test
    void 피드_상세를_조회한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(3L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.feed.detail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.feed_no").value(3))
                .andExpect(jsonPath("$.rst_content.feed_details.size()").value(3));
    }

    @Test
    void 피드를_등록한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedTypeCd(FeedTypeCd.FEED)
                .rpstImgNo(0L)
                .hashTag(
                        List.of("#socail", "#benope")
                )
                .feedDetails(
                        List.of(
                                FeedDetailRequest.builder()
                                        .feedDetailContent("created")
                                        .uploadImgNo_1(1L)
                                        .uploadImgNo_2(2L)
                                        .build(),
                                FeedDetailRequest.builder()
                                        .feedDetailContent("created")
                                        .itemTag(
                                                List.of(
                                                        FeedDetailRequest.ItemTag.builder()
                                                                .foodNo(1L)
                                                                .userInput(100D)
                                                                .build()
                                                )
                                        )
                                        .build()
                        )
                )
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.feed_details[0].upload_img_no_1").value(1))
                .andExpect(jsonPath("$.rst_content.feed_details[0].upload_img_no_2").value(2))
                .andExpect(jsonPath("$.rst_content.feed_details[1].item_tag").isNotEmpty())
                .andExpect(jsonPath("$.rst_content.feed_details[1].item_tag[0].food_nm").value("20bar 초콜릿카라멜"))
                .andExpect(jsonPath("$.rst_content.feed_details[1].item_tag[0].brand").value("베노프"))
                .andExpect(jsonPath("$.rst_content.feed_details[1].item_tag[0].user_input").value(100))
                .andExpect(jsonPath("$.rst_content.feed_details.size()").value(2));
    }

    @Test
    void 피드를_수정한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(1L)
                .feedTypeCd(FeedTypeCd.FEED)
                .rpstImgNo(0L)
                .hashTag(
                        List.of("#socail", "#benope")
                )
                .feedDetails(
                        List.of(
                                FeedDetailRequest.builder()
                                        .uploadImgNo_1(10L)
                                        .uploadImgNo_2(11L)
                                        .feedDetailContent("content1")
                                        .build(),
                                FeedDetailRequest.builder()
                                        .itemTag(List.of())
                                        .feedDetailContent("content2")
                                        .build()
                        )
                )
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.feed")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.feed_details.size()").value(2))
                .andExpect(jsonPath("$.rst_content.feed_details[0].upload_img_no_1").value(10))
                .andExpect(jsonPath("$.rst_content.feed_details[0].upload_img_no_2").value(11))
                .andExpect(jsonPath("$.rst_content.feed_details[1].item_tag").isEmpty())
                .andExpect(jsonPath("$.rst_content.feed_details[2]").doesNotExist());
    }

    @Test
    void 피드를_삭제한다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.feed")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        em.clear();

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        mockMvc.perform(
                        post("/get.feed.detail")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void 영양성분을_계산한다() throws Exception {
        // Given
        List<FeedDetailRequest.ItemTag> itemTag1 = List.of(
                FeedDetailRequest.ItemTag.builder()
                        .foodNo(1L)
                        .userInput(100D)
                        .build()
        );

        List<FeedDetailRequest.ItemTag> itemTag2 = List.of(
                FeedDetailRequest.ItemTag.builder()
                        .foodNo(2L)
                        .userInput(200D)
                        .build()
        );

        FeedRequest cond = FeedRequest.builder()
                .feedTypeCd(FeedTypeCd.FEED)
                .rpstImgNo(0L)
                .feedDetails(
                        List.of(
                                FeedDetailRequest.builder()
                                        .feedDetailContent("created")
                                        .itemTag(itemTag1)
                                        .build(),
                                FeedDetailRequest.builder()
                                        .feedDetailContent("created")
                                        .itemTag(itemTag2)
                                        .build()
                        )
                )
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.nutrient_view.calories").value("150kcal"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.fat").value("150g(포화지방 150g)"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.carbohydrate").value("알 수 없음"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.protein").value("150g"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.food_summaries.size()").value("2"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.food_summaries[0].per_serving").value("1회 서빙량(100.0g)"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.food_summaries[0].calories").value("50kcal"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.food_summaries[1].per_serving").value("1회 서빙량(200.0l)"))
                .andExpect(jsonPath("$.rst_content.nutrient_view.food_summaries[1].calories").value("100kcal"));
    }

    @Test
    void 피드_등록_시_식품_태그_횟수를_증가시킨다() throws Exception {
        // Given
        List<FeedDetailRequest.ItemTag> itemTag = List.of(
                FeedDetailRequest.ItemTag.builder()
                        .foodNo(1L)
                        .userInput(100D)
                        .build(),
                FeedDetailRequest.ItemTag.builder()
                        .foodNo(2L)
                        .userInput(100D)
                        .build()
        );

        FeedRequest cond = FeedRequest.builder()
                .feedTypeCd(FeedTypeCd.FEED)
                .rpstImgNo(1L)
                .feedDetails(
                        List.of(
                                FeedDetailRequest.builder()
                                        .feedDetailContent("created")
                                        .itemTag(itemTag)
                                        .build()
                        )
                )
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        Food food_1 = foodJpaRepository.getById(1L);
        assertThat(food_1.getTagCount()).isEqualTo(2);

        Food food_2 = foodJpaRepository.getById(2L);
        assertThat(food_2.getTagCount()).isEqualTo(2);
    }

    @Test
    void 피드_삭제_시_식품_태그_횟수를_감소시킨다() throws Exception {
        // Given
        FeedRequest cond = FeedRequest.builder()
                .feedNo(3L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/del.feed")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        Food food_1 = foodJpaRepository.getById(1L);
        assertThat(food_1.getTagCount()).isEqualTo(0);

        Food food_2 = foodJpaRepository.getById(2L);
        assertThat(food_2.getTagCount()).isEqualTo(0);
    }

    @Test
    void 중복_태그_시_식품_태그_횟수를_유지한다() throws Exception {
        // Given
        List<FeedDetailRequest.ItemTag> itemTag = List.of(
                FeedDetailRequest.ItemTag.builder()
                        .foodNo(1L)
                        .userInput(100D)
                        .build(),
                FeedDetailRequest.ItemTag.builder()
                        .foodNo(2L)
                        .userInput(100D)
                        .build()
        );

        FeedRequest cond = FeedRequest.builder()
                .feedTypeCd(FeedTypeCd.FEED)
                .rpstImgNo(1L)
                .feedDetails(
                        List.of(
                                FeedDetailRequest.builder()
                                        .feedDetailContent("created")
                                        .itemTag(itemTag)
                                        .build()
                        )
                )
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/reg.feed")
                                .header("Authorization", "Bearer " + accessToken(2L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        Food food_1 = foodJpaRepository.getById(1L);
        assertThat(food_1.getTagCount()).isEqualTo(1);

        Food food_2 = foodJpaRepository.getById(2L);
        assertThat(food_2.getTagCount()).isEqualTo(1);
    }

}