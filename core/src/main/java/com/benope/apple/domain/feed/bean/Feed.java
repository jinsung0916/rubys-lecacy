package com.benope.apple.domain.feed.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.feed.event.FeedDeleteEvent;
import com.benope.apple.domain.feed.event.FeedRegistEvent;
import com.benope.apple.domain.feed.event.FeedUpdateEvent;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "feed")
@SQLDelete(sql = "UPDATE feed SET row_stat_cd = 'D' WHERE feed_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_no")
    private Long feedNo;
    @Column(name = "mb_no")
    private Long mbNo;
    @Enumerated(EnumType.STRING)
    private FeedTypeCd feedTypeCd;
    private String feedTitle;
    @Column(name = "rpst_img_no")
    private Long rpstImgNo;
    @Convert(converter = HashTagConverter.class)
    @Column(columnDefinition = "json")
    private List<String> hashTag;
    private Long viewCnt;
    private Long commentCnt;
    private Long likeCnt;
    private Long scrapCnt;
    private String hideYn;
    private String hideResnCd;
    private String hideResnDtls;
    private LocalDate hideDt;

    @ElementCollection
    @CollectionTable(
            name = "feed_detail",
            joinColumns = {
                    @JoinColumn(
                            name = "feed_no",
                            referencedColumnName = "feed_no",
                            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
                    )
            }
    )
    @OrderColumn(name = "feed_detail_ord", columnDefinition = "BIGINT")
    private List<FeedDetail> feedDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "mb_no",
            referencedColumnName = "mb_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "rpst_img_no",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg rpstImg;

    public List<FeedDetail> getFeedDetails() {
        return DomainObjectUtil.nullSafeStream(this.feedDetails)
                .filter(Objects::nonNull)
                .collect(Collectors.toUnmodifiableList());
    }

    public String getMemberNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String getMemberProfileImageUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getProfileImgUrl);
    }

    public String getRpstImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.rpstImg, UploadImg::getImgFileUrl);
    }

    @Override
    protected void beforeCreate() {
        validate();

        this.viewCnt = 0L;
        this.commentCnt = 0L;
        this.likeCnt = 0L;
        this.scrapCnt = 0L;

        registerEvent(new FeedRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        validate();

        registerEvent(new FeedUpdateEvent(this));
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new FeedDeleteEvent(this));
    }

    private void validate() {
        Assert.notNull(mbNo);
        Assert.notNull(feedTypeCd);
        Assert.notNull(rpstImgNo);
        Assert.notNull(feedDetails);
    }

}