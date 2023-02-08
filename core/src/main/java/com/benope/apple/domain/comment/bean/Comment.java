package com.benope.apple.domain.comment.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.alarm.bean.AlarmTypeCd;
import com.benope.apple.domain.alarm.bean.Notifiable;
import com.benope.apple.domain.member.bean.ExpertCd;
import com.benope.apple.domain.member.bean.Member;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "comment")
@SecondaryTable(
        name = "comment_relation",
        pkJoinColumns = {
                @PrimaryKeyJoinColumn(
                        name = "child_comment_no",
                        referencedColumnName = "comment_no",
                        foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
                )
        }
)
@Where(clause = NOT_DELETED_CLAUSE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "comment_type_cd")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends AbstractDomain implements Notifiable {

    public static Long INITIAL_PARENT_COMMENT_NO = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private Long commentNo;
    @Column(name = "comment_type_cd", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private CommentTypeCd commentTypeCd;
    @Column(name = "mb_no")
    private Long mbNo;
    @Column(name = "object_no", insertable = false, updatable = false)
    private Long objectNo;
    private String contents;
    private Long subCommentCnt;
    private String hideYn;
    private String hideResnCd;
    private String hideResnDtls;
    private LocalDate hideDt;

    @Column(table = "comment_relation")
    private Long parentCommentNo;
    @Column(table = "comment_relation")
    private Integer reorderNo;
    @Column(table = "comment_relation")
    private Byte depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "mb_no",
            referencedColumnName = "mb_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Member member;

    public String getMemberNickname() {
        String nickname = DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getNickname);
        return Objects.nonNull(nickname) ? nickname : Member.NULL_MEMBER.getNickname();
    }

    public String getMemberProfileImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getProfileImgUrl);
    }

    public ExpertCd getMemberExpertCd() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getExpertCd);
    }

    public boolean isSubComment() {
        return !INITIAL_PARENT_COMMENT_NO.equals(this.parentCommentNo);
    }

    @Override
    public AlarmTypeCd toAlarmTypeCd() {
        if (isSubComment()) {
            return AlarmTypeCd.SUB_COMMENT;
        } else {
            return AlarmTypeCd.COMMENT;
        }
    }

    @Override
    public String toMessage() {
        if (isSubComment()) {
            return "%s님이 회원님의 댓글에 답글을 남겼습니다: " + toEscapedContents();
        } else {
            return "%s님이 댓글을 남겼습니다: " + toEscapedContents();
        }
    }

    private String toEscapedContents() {
        return getContents().replaceAll("%", "%%");
    }

    @Override
    protected void beforeCreate() {
        validate();

        this.subCommentCnt = 0L;
        this.hideYn = "N";

        if (isRoot()) {
            this.parentCommentNo = INITIAL_PARENT_COMMENT_NO;
        }
    }

    private boolean isRoot() {
        return Objects.isNull(this.parentCommentNo);
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(mbNo);
        Assert.notNull(contents);
        Assert.notNull(reorderNo);
        Assert.notNull(depth);
    }

}