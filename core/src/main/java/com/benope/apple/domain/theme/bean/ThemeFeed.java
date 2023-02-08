package com.benope.apple.domain.theme.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.feed.bean.Feed;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Audited
@Entity
@Table(name = "theme_feed")
@SQLDelete(sql = "UPDATE theme_feed SET row_stat_cd = 'D' WHERE theme_feed_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeFeed extends AbstractDomain {

    private static final long DEFAULT_REORDER_NO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeFeedNo;
    private Long themeNo;
    @Column(name = "feed_no")
    private Long feedNo;
    private Long reorderNo;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "feed_no",
            referencedColumnName = "feed_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Feed feed;

    @Override
    protected void beforeCreate() {
        validate();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(themeNo);
        Assert.notNull(feedNo);
        Assert.notNull(reorderNo);
    }
}