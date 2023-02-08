package com.benope.apple.api.member.controller;

import com.benope.apple.api.AppleTest;
import com.benope.apple.api.member.bean.MemberRequest;
import com.benope.apple.api.member.bean.MemberSearchRequest;
import com.benope.apple.domain.member.bean.GenderCd;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("MemberControllerTest.sql")
public class MemberControllerTest extends AppleTest {

    @Test
    public void 회원_정보를_조회한다() throws Exception {
        // Given
        MemberSearchRequest cond = MemberSearchRequest.builder()
                .searchMbNo(1L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.mb_no").value(1))
                .andExpect(jsonPath("$.rst_content.nickname").value("example"))
                .andExpect(jsonPath("$.rst_content.email").value("example@example.com"))
                .andExpect(jsonPath("$.rst_content.phone_number1").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.phone_number2").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.phone_number3").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.profile_img_no").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.gender_cd").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.birthday").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.profile_text").hasJsonPath())
                .andExpect(jsonPath("$.rst_content.post_cnt").value(0))
                .andExpect(jsonPath("$.rst_content.follower_cnt").value(1))
                .andExpect(jsonPath("$.rst_content.following_cnt").value(1))
                .andExpect(jsonPath("$.rst_content.exp").value(0))
                .andExpect(jsonPath("$.rst_content.expert_info.expert_policy_nm").value("프로식단러"));
    }

    @Test
    public void 존재하지_않는_회원을_조회할_경우_400을_반환한다() throws Exception {
        // Given
        MemberSearchRequest cond = MemberSearchRequest.builder()
                .searchMbNo(3L)
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/get.member")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void 회원_정보를_수정한다() throws Exception {
        // Given
        MemberRequest cond = MemberRequest.builder()
                .nickname("nickname")
                .email("email@naver.com")
                .phoneNumber1("010")
                .phoneNumber2("0000")
                .phoneNumber3("0000")
                .profileImgNo(1L)
                .genderCd(GenderCd.MALE)
                .birthday("99991231")
                .profileText("profileText")
                .build();

        // When
        ResultActions resultActions = mockMvc.perform(
                        post("/mod.member")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is2xxSuccessful());

        MemberSearchRequest check = MemberSearchRequest.builder()
                .searchMbNo(1L)
                .build();

        mockMvc.perform(
                        post("/get.member")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(check))
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.rst_content.mb_no").value(1))
                .andExpect(jsonPath("$.rst_content.nickname").value(cond.getNickname()))
                .andExpect(jsonPath("$.rst_content.email").value(cond.getEmail()))
                .andExpect(jsonPath("$.rst_content.phone_number1").value(cond.getPhoneNumber1()))
                .andExpect(jsonPath("$.rst_content.phone_number2").value(cond.getPhoneNumber2()))
                .andExpect(jsonPath("$.rst_content.phone_number3").value(cond.getPhoneNumber3()))
                .andExpect(jsonPath("$.rst_content.profile_img_no").value(cond.getProfileImgNo()))
                .andExpect(jsonPath("$.rst_content.gender_cd").value(cond.getGenderCd().toString()))
                .andExpect(jsonPath("$.rst_content.birthday").value(cond.getBirthday()))
                .andExpect(jsonPath("$.rst_content.profile_text").value(cond.getProfileText()));
    }

    @Test
    public void 중복된_닉네임을_요청을_경우_400을_반환한다() throws Exception {
        // Given
        MemberRequest cond = MemberRequest.builder()
                .nickname("example")
                .build();

        // When
        ResultActions resultActions =
        mockMvc.perform(
                        post("/mod.member")
                                .header("Authorization", "Bearer " + accessToken(1L))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                                .content(mapper.writeValueAsString(cond))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().is4xxClientError());
    }

}
