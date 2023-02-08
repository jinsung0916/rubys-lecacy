package com.benope.apple.api.member.bean;

import com.benope.apple.domain.member.bean.GenderCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSolrEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class MemberSimpleResponse {
    private Long mbNo;
    private String nickname;
    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private Long profileImgNo;
    private GenderCd genderCd;
    private String birthday;
    private String profileText;
    private String cafe24ID;
    private String fcm_token;

    private String profileImgUrl;

    public static MemberSimpleResponse fromEntity(Member member) {
        return MemberSimpleResponse.builder()
                .mbNo(member.getMbNo())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber1(member.getPhoneNumber_1())
                .phoneNumber2(member.getPhoneNumber_2())
                .phoneNumber3(member.getPhoneNumber_3())
                .profileImgNo(member.getProfileImageNo())
                .genderCd(member.getGenderCd())
                .birthday(member.getBirthday())
                .profileText(member.getProfileText())
                .cafe24ID(member.getCafe24Id())
                .profileImgUrl(member.getProfileImgUrl())
                .build();
    }

    public static MemberSimpleResponse fromEntity(MemberSolrEntity member) {
        return MemberSimpleResponse.builder()
                .mbNo(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber1(member.getPhoneNumber_1())
                .phoneNumber2(member.getPhoneNumber_2())
                .phoneNumber3(member.getPhoneNumber_3())
                .profileImgNo(member.getProfileImageNo())
                .genderCd(member.getGenderCd())
                .birthday(member.getBirthday())
                .profileText(member.getProfileText())
                .cafe24ID(member.getCafe24Id())
                .profileImgUrl(member.getProfileImgUrl())
                .build();
    }
}
