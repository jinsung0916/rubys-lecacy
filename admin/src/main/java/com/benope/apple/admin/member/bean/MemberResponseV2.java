package com.benope.apple.admin.member.bean;

import com.benope.apple.domain.loginHistory.bean.LoginHistory;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.domain.member.bean.MemberSummaryComposite;
import com.benope.apple.domain.member.bean.MemberTermAgree;
import com.benope.apple.domain.term.bean.Term;
import com.benope.apple.utils.DateTimeUtil;
import com.benope.apple.utils.DomainObjectUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Delegate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Builder(access = AccessLevel.PROTECTED)
public class MemberResponseV2 {

    @Delegate
    @JsonIgnore
    private MemberSimpleResponseV2 memberSimpleResponse;

    private ZonedDateTime lastAccessAt;

    private List<MemberTermAgreeResponse> memberTermAgrees;

    private long totalScoreCnt;
    private long monthScoreCnt;

    private long totalFoodCommentCnt;
    private long monthFoodCommentCnt;

    private long totalPostCnt;
    private long monthPostCnt;

    private long totalTagCnt;
    private long monthTagCnt;

    private long totalCommentCnt;
    private long monthCommentCnt;

    private long totalFollowerCnt;
    private long monthFollowerCnt;

    private long totalFollowingCnt;
    private long monthFollowingCnt;

    private long totalScrapCnt;
    private long monthScrapCnt;

    public static MemberResponseV2 fromEntity(Member entity) {
        return MemberResponseV2.builder()
                .memberSimpleResponse(MemberSimpleResponseV2.fromEntity(entity))
                .build();
    }

    public static MemberResponseV2 fromEntityToDetail(Member entity) {
        return MemberResponseV2.builder()
                .memberSimpleResponse(MemberSimpleResponseV2.fromEntity(entity))
                .memberTermAgrees(
                        DomainObjectUtil.nullSafeStream(entity.getLatestTermAgrees())
                                .map(MemberTermAgreeResponse::fromEntity)
                                .collect(Collectors.toUnmodifiableList())
                )
                .build();
    }

    public MemberResponseV2 setLoginHistory(LoginHistory history) {
        if(Objects.nonNull(history) && Objects.nonNull(history.getLastAccessAt())) {
            this.lastAccessAt = ZonedDateTime.of(history.getLastAccessAt(), DateTimeUtil.ZONE_ID);
        }

        return this;
    }

    public MemberResponseV2 setTotalSummary(MemberSummaryComposite summaries) {
        this.totalScoreCnt = summaries.getScoreCnt();
        this.totalFoodCommentCnt = summaries.getFoodCommentCnt();
        this.totalPostCnt = summaries.getPostCnt();
        this.totalTagCnt = summaries.getTagCnt();
        this.totalCommentCnt = summaries.getCommentCnt();
        this.totalFollowerCnt = summaries.getFollowerCnt();
        this.totalFollowingCnt = summaries.getFollowingCnt();
        this.totalScrapCnt = summaries.getScrapCnt();

        return this;
    }

    public MemberResponseV2 setMonthSummary(MemberSummaryComposite summaries) {
        this.monthScoreCnt = summaries.getScoreCnt();
        this.monthFoodCommentCnt = summaries.getFoodCommentCnt();
        this.monthPostCnt = summaries.getPostCnt();
        this.monthTagCnt = summaries.getTagCnt();
        this.monthCommentCnt = summaries.getCommentCnt();
        this.monthFollowerCnt = summaries.getFollowerCnt();
        this.monthFollowingCnt = summaries.getFollowingCnt();
        this.monthScrapCnt = summaries.getScrapCnt();

        return this;
    }

    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    public static class MemberTermAgreeResponse {

        private Term.TermCd termCd;
        private String termVersion;
        private boolean termAgree;
        private LocalDate termAgreeDt;

        public static MemberTermAgreeResponse fromEntity(MemberTermAgree entity) {
            return MemberTermAgreeResponse.builder()
                    .termCd(entity.getTermCd())
                    .termVersion(entity.getTermVersion())
                    .termAgree(entity.isAgree())
                    .termAgreeDt(entity.getTermAgreeDt())
                    .build();
        }

    }

}
