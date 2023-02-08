package com.benope.apple.domain.comment.bean;

import com.benope.apple.domain.comment.event.CommunityCommentDeleteEvent;
import com.benope.apple.domain.comment.event.CommunityCommentRegistEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.internal.util.Assert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PreRemove;

@Entity
@DiscriminatorValue("COMMUNITY")
@SQLDelete(sql = "UPDATE comment SET row_stat_cd = 'D' WHERE comment_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityComment extends Comment {

    @Column(name = "object_no")
    private Long communityNo;

    @Override
    public CommentTypeCd getCommentTypeCd() {
        return CommentTypeCd.COMMUNITY;
    }

    @Override
    public Long getObjectNo() {
        return communityNo;
    }

    @Override
    protected void beforeCreate() {
        super.beforeCreate();

        validate();

        registerEvent(new CommunityCommentRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        super.beforeUpdate();

        validate();
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new CommunityCommentDeleteEvent(this));
    }


    private void validate() {
        Assert.notNull(communityNo);
    }

}
