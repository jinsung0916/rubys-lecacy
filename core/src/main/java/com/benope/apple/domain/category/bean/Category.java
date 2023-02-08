package com.benope.apple.domain.category.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Audited
@Entity
@Table(name = "category")
@SQLDelete(sql = "UPDATE category SET row_stat_cd = 'D' WHERE category_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "category_type_cd")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends AbstractDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final Long ROOT_CATEGORY_NO = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private Long categoryNo;
    @Enumerated(EnumType.STRING)
    @Column(name = "category_type_cd", insertable = false, updatable = false)
    private CategoryTypeCd categoryTypeCd;
    @Column(name = "parent_category_no")
    private Long parentCategoryNo;
    private String categoryNm;
    @Column(name = "icon_img_no")
    private @Setter Long iconImgNo;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "parent_category_no",
            referencedColumnName = "category_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private Category parent;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "icon_img_no",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg iconImg;

    public String getParentCategoryNm() {
        return DomainObjectUtil.nullSafeEntityFunction(this.parent, Category::getCategoryNm);
    }

    public String getIconImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(iconImg, UploadImg::getImgFileUrl);
    }

    @Override
    protected void beforeCreate() {
        validate();

        if (isRoot()) {
            this.parentCategoryNo = ROOT_CATEGORY_NO;
        }
    }

    private boolean isRoot() {
        return Objects.isNull(this.parentCategoryNo);
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    protected void validate() {
        Assert.notNull(categoryNm);
    }

}