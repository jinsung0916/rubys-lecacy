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
@DiscriminatorValue("FOOD")
@SQLDelete(sql = "UPDATE report SET row_stat_cd = 'D' WHERE report_no = ? AND opt_lock = ?")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodReport extends Report{

    @Column(name = "report_content_1")
    private String brand;
    @Column(name = "report_content_2")
    private String foodNm;

    @Override
    protected void validate() {
        super.validate();

        Assert.notNull(brand);
        Assert.notNull(foodNm);
    }
}
