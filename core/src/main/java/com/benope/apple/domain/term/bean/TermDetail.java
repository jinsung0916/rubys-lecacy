package com.benope.apple.domain.term.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.utils.DateTimeUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "term_detail")
@SQLDelete(sql = "UPDATE term_detail SET row_stat_cd = 'D' WHERE term_cd = ? AND term_version = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TermDetail extends AbstractDomain implements Comparable<TermDetail> {

    @EmbeddedId
    private TermDetailId termDetailId;
    @Column(columnDefinition = "LONGTEXT")
    private String termContent;
    private LocalDate startDate;
    private LocalDate endDate;

    public Term.TermCd getTermCd() {
        return termDetailId.getTermCd();
    }

    public String getTermVersion() {
        return termDetailId.getTermVersion();
    }

    public boolean isValid() {
        if (isDatePropertiesNotNull()) {
            LocalDate today = DateTimeUtil.getCurrentDate();
            return today.isEqual(getStartDate())
                    || (today.isAfter(getStartDate()) && today.isBefore(getEndDate()))
                    || today.isEqual(getEndDate());
        } else {
            return false;
        }
    }

    private boolean isDatePropertiesNotNull() {
        return Objects.nonNull(getStartDate()) && Objects.nonNull(getEndDate());
    }

    @Override
    protected void beforeCreate() {
        validate();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(termDetailId);
        Assert.notNull(termContent);
        Assert.notNull(startDate);
        Assert.notNull(endDate);
    }

    @Override
    public int compareTo(TermDetail o) {
        return Comparator.comparing(TermDetail::getTermCd)
                .thenComparing(TermDetail::getTermVersion)
                .compare(this, o);
    }
}