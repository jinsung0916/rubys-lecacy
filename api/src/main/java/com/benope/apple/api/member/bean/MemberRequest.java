package com.benope.apple.api.member.bean;

import com.benope.apple.domain.member.bean.GenderCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.SessionUtil;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequest {

    @Length(min = 2, max = 16)
    @Pattern(regexp = "^[a-zA-Z\\d가-힣ㄱ-ㅎㅏ-ㅣ._]*$")
    private String nickname;
    @Email
    private String email;
    @Length(max = 4)
    private String phoneNumber1;
    @Length(max = 4)
    private String phoneNumber2;
    @Length(max = 4)
    private String phoneNumber3;
    private Long profileImgNo;
    private GenderCd genderCd;
    @Length(min = 8, max = 8)
    private String birthday;
    @Length(max = 300)
    private String profileText;

    public Member toEntity() {
        return Member.builder()
                .mbNo(SessionUtil.getSessionMbNo())
                .nickname(nickname)
                .email(email)
                .phoneNumber_1(phoneNumber1)
                .phoneNumber_2(phoneNumber2)
                .phoneNumber_3(phoneNumber3)
                .profileImageNo(profileImgNo)
                .genderCd(genderCd)
                .birthday(birthday)
                .profileText(profileText)
                .build();
    }

}
