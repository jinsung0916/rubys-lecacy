package com.benope.apple.domain.report.bean;

import com.benope.apple.config.domain.AbstractDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "report")
@Where(clause = NOT_DELETED_CLAUSE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "report_target_cd")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportNo;
    private Long mbNo;
    @Enumerated(EnumType.STRING)
    @Column(name = "report_target_cd", insertable = false, updatable = false)
    private ReportTargetCd reportTargetCd;
    @Column(name = "report_target_no", insertable = false, updatable = false)
    private Long reportTargetNo;

    public String getContents() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void beforeCreate() {
        validate();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    protected void validate() {
        Assert.notNull(mbNo);
    }

}