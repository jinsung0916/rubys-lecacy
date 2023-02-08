package com.benope.apple.domain.image.bean;

import com.benope.apple.config.domain.AbstractDomain;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "upload_img")
@SQLDelete(sql = "UPDATE upload_img SET row_stat_cd = 'D' WHERE img_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImg extends AbstractDomain implements Serializable {

    private static final long serialVersionUID = -1L;

    public static final String BASE_URL = "https://rubys-apple-public.s3.ap-northeast-2.amazonaws.com";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_no")
    private Long imgNo;
    private String imgFileNm;
    private String imgFileExt;
    private String imgFilePath;

    public String getImgFileUrl() {
        return Objects.nonNull(imgFilePath)
                ? UriComponentsBuilder.fromHttpUrl(BASE_URL).path(imgFilePath).toUriString()
                : null;
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
        Assert.notNull(imgFileNm);
        Assert.notNull(imgFileExt);
        Assert.notNull(imgFilePath);
    }
}
