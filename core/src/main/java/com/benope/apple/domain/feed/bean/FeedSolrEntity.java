package com.benope.apple.domain.feed.bean;

import com.benope.apple.config.domain.AbstractDocument;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SolrDocument(collection = "feed")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedSolrEntity extends AbstractDocument {

    @Id
    private Long id;
    @Indexed(type = "plong")
    private Long mbNo;
    @Indexed(type = "string")
    private FeedTypeCd feedTypeCd;
    @Indexed(type = "string", copyTo = {"_text_", "_text_str_"})
    private String feedTitle;
    @Indexed(type = "plong")
    private Long rpstImgNo;
    @Indexed(type = "strings", copyTo = {"_text_", "_text_str_"})
    private List<String> hashTag;
    @Indexed(type = "plong")
    private Long viewCnt;
    @Indexed(type = "plong")
    private Long commentCnt;
    @Indexed(type = "plong")
    private Long likeCnt;
    @Indexed(type = "plong")
    private Long scrapCnt;
    @Indexed(type = "string")
    private String hideYn;
    @Indexed(type = "string")
    private String hideResnCd;
    @Indexed(type = "string")
    private String hideResnDtls;
    @Indexed(type = "pdate")
    private LocalDate hideDt;

    @Indexed(type = "strings", copyTo = {"_text_", "_text_str_"})
    private List<String> contents;
    @Indexed(type = "plongs")
    private List<Long> taggedFoods;

    @Transient
    private @Setter UploadImg rpstImg;

    @Transient
    private @Setter Member member;

    public static FeedSolrEntity fromEntity(Feed feed) {
        return FeedSolrEntity.builder()
                .id(feed.getFeedNo())
                .mbNo(feed.getMbNo())
                .feedTypeCd(feed.getFeedTypeCd())
                .feedTitle(feed.getFeedTitle())
                .rpstImgNo(feed.getRpstImgNo())
                .viewCnt(feed.getViewCnt())
                .hashTag(feed.getHashTag())
                .commentCnt(feed.getCommentCnt())
                .likeCnt(feed.getLikeCnt())
                .scrapCnt(feed.getScrapCnt())
                .hideYn(feed.getHideYn())
                .hideResnCd(feed.getHideResnCd())
                .hideResnDtls(feed.getHideResnDtls())
                .hideDt(feed.getHideDt())
                .createdAt(feed.getCreatedAt())
                .createdBy(feed.getCreatedBy())
                .updatedAt(feed.getUpdatedAt())
                .updatedBy(feed.getUpdatedBy())
                .rowStatCd(feed.getRowStatCd())
                .contents(toContents(feed.getFeedDetails()))
                .taggedFoods(toTaggedFoods(feed.getFeedDetails()))
                .build();
    }

    private static List<String> toContents(List<FeedDetail> feedDetails) {
        return DomainObjectUtil.nullSafeStream(feedDetails)
                .map(FeedDetail::getFeedDetailContent)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<Long> toTaggedFoods(List<FeedDetail> feedDetails) {
        return DomainObjectUtil.nullSafeStream(feedDetails)
                .map(FeedDetail::getItemTag)
                .flatMap(List::stream)
                .map(TaggedFood::getFoodNo)
                .collect(Collectors.toUnmodifiableList());
    }

    public String getRpstImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.rpstImg, UploadImg::getImgFileUrl);
    }

    public String getMemberNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String getMemberProfileImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getProfileImgUrl);
    }

}
