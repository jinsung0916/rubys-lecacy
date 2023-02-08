package com.benope.apple.domain.banner.bean;

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
import java.time.LocalDateTime;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Audited
@Entity
@Table(name = "banner")
@SQLDelete(sql = "UPDATE banner SET row_stat_cd = 'D' WHERE banner_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bannerNo;
    private Integer reorderNo;
    @Column(name = "banner_img_no")
    private @Setter Long bannerImgNo;
    private String linkUrl;
    private String bannerNm;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @NotAudited
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "banner_img_no",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg;

    public String getBannerImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(uploadImg, UploadImg::getImgFileUrl);
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
        Assert.notNull(reorderNo);
        Assert.notNull(bannerNm);
        Assert.notNull(startDateTime);
        Assert.notNull(endDateTime);
    }

}
