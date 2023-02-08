package com.benope.apple.config.domain;

import com.benope.apple.utils.DateTimeUtil;
import com.benope.apple.utils.DomainObjectUtil;
import com.benope.apple.utils.SessionUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractDomain {

    public static final String NOT_DELETED_CLAUSE = "row_stat_cd != 'D'";

    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
    @Enumerated(EnumType.STRING)
    private RowStatCd rowStatCd;

    @Version
    @ColumnDefault("0")
    private Long optLock;

    @Transient
    private transient final List<Object> domainEvents = new ArrayList<>();

    public String getName() {
        return String.valueOf(this.createdBy);
    }

    @PrePersist
    private void prePersist() {
        beforeCreate();

        this.createdAt = DateTimeUtil.getCurrentDateTime();
        this.createdBy = SessionUtil.getSessionMbNo();
        this.rowStatCd = RowStatCd.C;

        this.optLock = 0L;
    }

    protected abstract void beforeCreate();

    @PreUpdate
    private void preUpdate() {
        beforeUpdate();

        this.updatedAt = DateTimeUtil.getCurrentDateTime();
        this.updatedBy = SessionUtil.getSessionMbNo();
        this.rowStatCd = RowStatCd.U;
    }

    protected abstract void beforeUpdate();


    public final ZonedDateTime getZonedCreateAt() {
        return DomainObjectUtil.nullSafeFunction(this.createdAt, it -> ZonedDateTime.of(it, DateTimeUtil.ZONE_ID));
    }

    public final ZonedDateTime getZonedUpdatedAt() {
        return DomainObjectUtil.nullSafeFunction(this.updatedAt, it -> ZonedDateTime.of(it, DateTimeUtil.ZONE_ID));
    }

    protected <T> void registerEvent(T event) {
        Objects.requireNonNull(event);

        this.domainEvents.add(event);
    }

    @DomainEvents
    private Collection<Object> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @AfterDomainEventPublication
    private void clearDomainEvents() {
        this.domainEvents.clear();
    }

}
