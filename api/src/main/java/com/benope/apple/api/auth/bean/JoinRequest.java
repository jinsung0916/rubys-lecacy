package com.benope.apple.api.auth.bean;

import com.benope.apple.api.term.bean.MemberTermAgreeRequest;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberAuth;
import com.benope.apple.domain.member.bean.MemberCollectionContainer;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {

    @NotNull
    private MemberAuth.MbAuthCd type;
    @NotBlank
    private String idToken;
    @Email
    private String email;
    @NotBlank
    private String nickname;
    private Long profileImgNo;

    @Valid
    private List<MemberTermAgreeRequest> termAgreeList;

    public Member toMember() {
        return Member.builder()
                .email(email)
                .nickname(nickname)
                .profileImageNo(profileImgNo)
                .container(
                        MemberCollectionContainer.builder()
                                .memberAuths(
                                        List.of(
                                                MemberAuth.builder()
                                                        .mbAuthCd(type)
                                                        .identifier(idToken)
                                                        .build()
                                        )
                                )
                                .memberTermAgrees(
                                        DomainObjectUtil.nullSafeStream(termAgreeList)
                                                .map(MemberTermAgreeRequest::toEntity)
                                                .collect(Collectors.toUnmodifiableList())
                                )
                                .build()
                )
                .build();
    }

}
