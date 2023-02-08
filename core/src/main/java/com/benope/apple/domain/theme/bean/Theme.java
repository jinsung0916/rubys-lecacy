package com.benope.apple.domain.theme.bean;

import com.benope.apple.config.domain.AbstractDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.util.List;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Audited
@Entity
@Table(name = "theme")
@SQLDelete(sql = "UPDATE theme SET row_stat_cd = 'D' WHERE theme_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Theme extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long themeNo;
    private String themeNm;
    private String displayNm;
    private String memo;
    private Long reorderNo;
    private Boolean picked;
    private Boolean displayAll; // 모든 피드를 최신순으로 노출한다.

    @Convert(converter = ThemeConditionConverter.class)
    @Column(columnDefinition = "json")
    private List<ThemeCondition> themeConditions;

    public boolean picked() {
        return Boolean.TRUE.equals(picked);
    }

    public boolean displayAll() {
        return Boolean.TRUE.equals(displayAll);
    }

    public boolean batchCollected() {
        return themeConditions.size() > 0;
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
        Assert.notNull(themeNm);
        Assert.notNull(displayNm);
        Assert.notNull(reorderNo);
    }

}