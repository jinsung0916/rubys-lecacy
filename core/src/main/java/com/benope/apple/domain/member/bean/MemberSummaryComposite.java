package com.benope.apple.domain.member.bean;

import com.benope.apple.utils.DomainObjectUtil;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberSummaryComposite {

    private final List<MemberSummary> memberSummaries;

    public long getFollowerCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.FOLLOWER);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getFollowingCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.FOLLOWING);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getPostCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.POST);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getScrapCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.SCRAP);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getScoreCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.SCORE);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getCommentCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.COMMENT);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getFoodCommentCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.FOOD_COMMENT);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    public long getTagCnt() {
        Optional<MemberSummary> optional = getMemberSummary(MbSummaryCd.TAG);
        if (optional.isPresent()) {
            return optional.get().getSummaryCnt();
        } else {
            return 0;
        }
    }

    private Optional<MemberSummary> getMemberSummary(MbSummaryCd mbSummaryCd) {
        return DomainObjectUtil.nullSafeStream(this.memberSummaries)
                .filter(it -> mbSummaryCd.equals(it.getMbSummaryCd()))
                .findAny();
    }

}
