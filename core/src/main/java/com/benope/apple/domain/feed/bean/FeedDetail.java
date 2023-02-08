package com.benope.apple.domain.feed.bean;

import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Embeddable
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedDetail {

    @Column(name = "upload_img_no_1")
    private Long uploadImgNo_1;
    @Column(name = "upload_img_no_2")
    private Long uploadImgNo_2;
    @Column(name = "upload_img_no_3")
    private Long uploadImgNo_3;
    @Column(name = "upload_img_no_4")
    private Long uploadImgNo_4;
    @Column(name = "upload_img_no_5")
    private Long uploadImgNo_5;
    @Convert(converter = UploadImgFoodRelationConverter.class)
    @Column(columnDefinition = "json")
    private List<UploadImgFoodRelation> uploadImgFoodRelation_1;
    @Convert(converter = UploadImgFoodRelationConverter.class)
    @Column(columnDefinition = "json")
    private List<UploadImgFoodRelation> uploadImgFoodRelation_2;
    @Convert(converter = UploadImgFoodRelationConverter.class)
    @Column(columnDefinition = "json")
    private List<UploadImgFoodRelation> uploadImgFoodRelation_3;
    @Convert(converter = UploadImgFoodRelationConverter.class)
    @Column(columnDefinition = "json")
    private List<UploadImgFoodRelation> uploadImgFoodRelation_4;
    @Convert(converter = UploadImgFoodRelationConverter.class)
    @Column(columnDefinition = "json")
    private List<UploadImgFoodRelation> uploadImgFoodRelation_5;
    private String feedDetailTitle;
    private String feedDetailContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "upload_img_no_1",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg_1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "upload_img_no_2",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg_2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "upload_img_no_3",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg_3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "upload_img_no_4",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg_4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "upload_img_no_5",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg uploadImg_5;

    public String getUploadImgUrl_1() {
        return DomainObjectUtil.nullSafeEntityFunction(this.uploadImg_1, UploadImg::getImgFileUrl);
    }

    public String getUploadImgUrl_2() {
        return DomainObjectUtil.nullSafeEntityFunction(this.uploadImg_2, UploadImg::getImgFileUrl);
    }

    public String getUploadImgUrl_3() {
        return DomainObjectUtil.nullSafeEntityFunction(this.uploadImg_3, UploadImg::getImgFileUrl);
    }

    public String getUploadImgUrl_4() {
        return DomainObjectUtil.nullSafeEntityFunction(this.uploadImg_4, UploadImg::getImgFileUrl);
    }

    public String getUploadImgUrl_5() {
        return DomainObjectUtil.nullSafeEntityFunction(this.uploadImg_5, UploadImg::getImgFileUrl);
    }

    public List<TaggedFood> getItemTag() {
        return DomainObjectUtil.nullSafeStream(getUploadImgFoodRelations())
                .map(UploadImgFoodRelation::toTaggedFood)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<UploadImgFoodRelation> getUploadImgFoodRelations() {
        return Stream.of(uploadImgFoodRelation_1,
                        uploadImgFoodRelation_2,
                        uploadImgFoodRelation_3,
                        uploadImgFoodRelation_4,
                        uploadImgFoodRelation_5)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .collect(Collectors.toUnmodifiableList());
    }

}