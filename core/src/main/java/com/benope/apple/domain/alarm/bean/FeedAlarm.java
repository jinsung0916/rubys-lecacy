package com.benope.apple.domain.alarm.bean;

import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.feed.bean.FeedTypeCd;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;

@Entity
@DiscriminatorValue("FEED")
@SQLDelete(sql = "UPDATE alarm SET row_stat_cd = 'D' WHERE alarm_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedAlarm extends Alarm {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "object_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Feed feed;

    @Column(name = "object_no")
    private Long feedNo;

    public String getAlarmTargetDetailCd() {
        FeedTypeCd feedTypeCd = DomainObjectUtil.nullSafeEntityFunction(this.feed, Feed::getFeedTypeCd);
        return DomainObjectUtil.nullSafeFunction(feedTypeCd, FeedTypeCd::name);
    }

    @Override
    protected void validate() {
        super.validate();

        Assert.notNull(feedNo);
    }
}
