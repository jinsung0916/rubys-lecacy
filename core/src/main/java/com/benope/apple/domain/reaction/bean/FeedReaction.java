package com.benope.apple.domain.reaction.bean;

import com.benope.apple.domain.reaction.event.FeedReactionDeleteEvent;
import com.benope.apple.domain.reaction.event.FeedReactionRegistEvent;
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
@SQLDelete(sql = "UPDATE reaction SET row_stat_cd = 'D' WHERE reaction_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedReaction extends Reaction {

    @Column(name = "object_no")
    private Long feedNo;

    public static Reaction getLikeInstance(Long mbNo, Long feedNo) {
        return FeedReaction.builder()
                .mbNo(mbNo)
                .feedNo(feedNo)
                .reactionTypeCd(ReactionTypeCd.LIKE)
                .build();
    }

    @Override
    public ReactionTargetCd getReactionTargetCd() {
        return ReactionTargetCd.FEED;
    }

    @Override
    public Long getObjectNo() {
        return feedNo;
    }

    @Override
    protected void beforeCreate() {
        super.beforeCreate();

        validate();

        registerEvent(new FeedReactionRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        super.beforeUpdate();

        validate();
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new FeedReactionDeleteEvent(this));
    }

    private void validate() {
        Assert.notNull(feedNo);
    }

}
