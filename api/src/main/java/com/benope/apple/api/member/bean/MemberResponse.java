package com.benope.apple.api.member.bean;

import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import com.benope.apple.utils.DomainObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class MemberResponse {

    @Delegate
    @JsonIgnore
    private MemberSimpleResponse memberSimpleResponse;

    private String profileImgUrl;

    private long followerCnt;
    private long followingCnt;
    private long postCnt;
    private long scrapCnt;
    private long scoreCnt;
    private long commentCnt;
    private long tagCnt;
    private long exp;

    private ExpertResponse expertInfo;

    private @Setter boolean isFollow;

    public static MemberResponse fromEntity(Member member) {
        return MemberResponse.builder()
                .memberSimpleResponse(MemberSimpleResponse.fromEntity(member))
                .profileImgUrl(member.getProfileImgUrl())
                .expertInfo(
                        ExpertResponse.builder()
                                .expertPolicyNm(DomainObjectUtil.nullSafeFunction(member.getExpertCd(), ExpertCd::getDesc))
                                .build()
                )
                .build();
    }

    public void setMemberSummaries(MemberSummaryComposite memberSummaries) {
        this.followerCnt = memberSummaries.getFollowerCnt();
        this.followingCnt = memberSummaries.getFollowingCnt();
        this.postCnt = memberSummaries.getPostCnt();
        this.scrapCnt = memberSummaries.getScrapCnt();
        this.scoreCnt = memberSummaries.getScoreCnt();
        this.commentCnt = memberSummaries.getFoodCommentCnt();
        this.tagCnt = memberSummaries.getTagCnt();
        this.exp = 0L;
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class ExpertResponse {
        private String expertPolicyNm;
    }

}
