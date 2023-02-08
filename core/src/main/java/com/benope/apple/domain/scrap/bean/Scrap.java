package com.benope.apple.domain.scrap.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.feed.bean.Feed;
import com.benope.apple.domain.scrap.event.ScrapDeleteEvent;
import com.benope.apple.domain.scrap.event.ScrapRegistEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "scrap")
@SQLDelete(sql = "UPDATE scrap SET row_stat_cd = 'D' WHERE scrap_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap extends AbstractDomain {

    public static final long INITIAL_PARENT_DIRECTORY_NO = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scrapNo;
    @Enumerated(EnumType.STRING)
    private ScrapTypeCd scrapTypeCd;
    private Long parentDirectoryNo;
    private Long mbNo;
    @Column(name = "feed_no")
    private Long feedNo;
    private String directoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "feed_no",
            referencedColumnName = "feed_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private @Setter Feed feed;

    public boolean isScrap() {
        return ScrapTypeCd.SCRAP.equals(scrapTypeCd);
    }

    public boolean isDirectory() {
        return ScrapTypeCd.DIRECTORY.equals(scrapTypeCd);
    }

    @Override
    protected void beforeCreate() {
        validate();

        if (isRoot()) {
            this.parentDirectoryNo = INITIAL_PARENT_DIRECTORY_NO;
        }

        if (ScrapTypeCd.SCRAP.equals(this.scrapTypeCd)) {
            registerEvent(new ScrapRegistEvent(this));
        }
    }

    private boolean isRoot() {
        return Objects.isNull(this.parentDirectoryNo);
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    @PreRemove
    private void beforeDelete() {
        if (ScrapTypeCd.SCRAP.equals(this.scrapTypeCd)) {
            registerEvent(new ScrapDeleteEvent(this));
        }
    }

    private void validate() {
        Assert.notNull(scrapTypeCd);
        Assert.notNull(mbNo);

        if (ScrapTypeCd.SCRAP.equals(scrapTypeCd)) {
            Assert.notNull(feedNo);
        } else {
            Assert.notNull(directoryName);
        }
    }

}
