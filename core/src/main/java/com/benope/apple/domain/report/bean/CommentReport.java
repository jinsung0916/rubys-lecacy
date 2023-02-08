package com.benope.apple.domain.report.bean;

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

@Entity
@DiscriminatorValue("COMMENT")
@SQLDelete(sql = "UPDATE report SET row_stat_cd = 'D' WHERE report_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends Report {

    @Column(name = "report_target_no")
    private Long commentNo;
    @Column(name = "report_content_1")
    private String contents;

    @Override
    public ReportTargetCd getReportTargetCd() {
        return ReportTargetCd.COMMENT;
    }

    @Override
    public Long getReportTargetNo() {
        return commentNo;
    }

    @Override
    public String getContents() {
        return contents;
    }

    @Override
    protected void validate() {
        super.validate();

        Assert.notNull(commentNo);
        Assert.notNull(contents);
    }

}
