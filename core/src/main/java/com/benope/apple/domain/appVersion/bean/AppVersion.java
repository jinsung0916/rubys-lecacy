package com.benope.apple.domain.appVersion.bean;


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

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Audited
@Entity
@Table(name = "app_version")
@SQLDelete(sql = "UPDATE app_version SET row_stat_cd = 'D' WHERE app_version_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AppVersion extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appVersionNo;
    @Enumerated(EnumType.STRING)
    private OS os;
    private String version;
    private Boolean forceUpdate;
    private String memo;

    @Override
    protected void beforeCreate() {
        validate();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(os);
        Assert.notNull(version);
    }

    public enum OS {
        IOS, ANDROID
    }

}
