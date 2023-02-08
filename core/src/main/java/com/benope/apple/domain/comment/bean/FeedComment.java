package com.benope.apple.domain.comment.bean;

import com.benope.apple.domain.comment.event.FeedCommentDeleteEvent;
import com.benope.apple.domain.comment.event.FeedCommentRegistEvent;
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
@DiscriminatorValue("FEED")
@SQLDelete(sql = "UPDATE comment SET row_stat_cd = 'D' WHERE comment_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedComment extends Comment {

    @Column(name = "object_no")
    private Long feedNo;

    @Override
    public CommentTypeCd getCommentTypeCd() {
        return CommentTypeCd.FEED;
    }

    @Override
    public Long getObjectNo() {
        return feedNo;
    }

    @Override
    protected void beforeCreate() {
        super.beforeCreate();

        validate();

        registerEvent(new FeedCommentRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        super.beforeUpdate();

        validate();
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new FeedCommentDeleteEvent(this));
    }


    private void validate() {
        Assert.notNull(feedNo);
    }

}
