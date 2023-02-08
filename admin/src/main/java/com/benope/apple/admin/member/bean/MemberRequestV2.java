package com.benope.apple.admin.member.bean;

import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.member.bean.Member;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestV2 implements IdRequest {

    private List<Long> id;

    private String nickname;
    private ExpertCd expertCd;

    public Member toEntity(Long mbNo) {
        return Member.builder()
                .mbNo(mbNo)
                .nickname(nickname)
                .expertCd(expertCd)
                .build();
    }

}
