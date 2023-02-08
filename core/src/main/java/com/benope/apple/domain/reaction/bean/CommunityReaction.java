package com.benope.apple.domain.reaction.bean;

import com.benope.apple.domain.reaction.event.CommunityReactionDeleteEvent;
import com.benope.apple.domain.reaction.event.CommunityReactionRegistEvent;
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
@SQLDelete(sql = "UPDATE reaction SET row_stat_cd = 'D' WHERE reaction_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityReaction extends Reaction {

    @Column(name = "object_no")
    private Long communityNo;

    public static Reaction getLikeInstance(Long mbNo, Long communityNo) {
        return CommunityReaction.builder()
                .mbNo(mbNo)
                .communityNo(communityNo)
                .reactionTypeCd(ReactionTypeCd.LIKE)
                .build();
    }

    @Override
    public ReactionTargetCd getReactionTargetCd() {
        return ReactionTargetCd.COMMUNITY;
    }

    @Override
    public Long getObjectNo() {
        return communityNo;
    }

    @Override
    protected void beforeCreate() {
        super.beforeCreate();

        validate();

        registerEvent(new CommunityReactionRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        super.beforeUpdate();

        validate();
    }

    @PreRemove
    private void beforeDelete() {
        registerEvent(new CommunityReactionDeleteEvent(this));
    }

    private void validate() {
        Assert.notNull(communityNo);
    }

}
