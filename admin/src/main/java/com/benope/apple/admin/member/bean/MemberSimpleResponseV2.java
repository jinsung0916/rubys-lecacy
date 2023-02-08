package com.benope.apple.admin.member.bean;

import com.benope.apple.admin.file.bean.UploadImgResponseV2;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.member.bean.GenderCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class MemberSimpleResponseV2 {

    private Long id;
    private String nickname;
    private String email;
    private String phoneNumber1;
    private String phoneNumber2;
    private String phoneNumber3;
    private GenderCd genderCd;
    private String birthday;
    private String profileText;
    private String cafe24ID;
    private ExpertCd expertCd;

    private UploadImgResponseV2 profileImg;

    private MemberAuth.MbAuthCd mbAuthCd;
    private ZonedDateTime createdAt;

    public static MemberSimpleResponseV2 fromEntity(Member member) {
        return MemberSimpleResponseV2.builder()
                .id(member.getMbNo())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber1(member.getPhoneNumber_1())
                .phoneNumber2(member.getPhoneNumber_2())
                .phoneNumber3(member.getPhoneNumber_3())
                .genderCd(member.getGenderCd())
                .birthday(member.getBirthday())
                .profileText(member.getProfileText())
                .cafe24ID(member.getCafe24Id())
                .expertCd(member.getExpertCd())
                .profileImg(
                        UploadImgResponseV2.builder()
                                .imgNo(member.getProfileImageNo())
                                .src(member.getProfileImgUrl())
                                .build()
                )
                .mbAuthCd(
                        DomainObjectUtil.nullSafeStream(member.getMemberAuths())
                                .map(MemberAuth::getMbAuthCd)
                                .findFirst()
                                .orElse(null)
                )
                .createdAt(member.getZonedCreateAt())
                .build();
    }

}
