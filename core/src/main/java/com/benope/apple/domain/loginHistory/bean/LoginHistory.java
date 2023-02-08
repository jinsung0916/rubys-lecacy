package com.benope.apple.domain.loginHistory.bean;

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

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;


@Entity
@Table(name = "login_history")
@SQLDelete(sql = "UPDATE login_history SET row_stat_cd = 'D' WHERE login_history_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginHistory extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginHistoryNo;
    private Long mbNo;
    @Enumerated(EnumType.STRING)
    @Column(name = "login_chnl_cd")
    private LoginChannelCd loginChannelCd;
    @Enumerated(EnumType.STRING)
    private LoginHistoryCd loginHistoryCd;
    private String loginHistoryDtls;
    private String loginUserAgent;
    private LocalDate loginDt;
    private String loginIp;
    private String refreshToken;
    private String fcmToken;
    private LocalDateTime lastAccessAt;

    @Override
    protected void beforeCreate() {
        validate();

        this.loginDt = DateTimeUtil.getCurrentDate();
        this.lastAccessAt = DateTimeUtil.getCurrentDateTime();
    }

    @Override
    protected void beforeUpdate() {
        validate();
    }

    private void validate() {
        Assert.notNull(mbNo);
        Assert.notNull(loginChannelCd);
        Assert.notNull(loginHistoryCd);
    }

    public enum LoginChannelCd {
        APP, WEB, CAFE24
    }

    public enum LoginHistoryCd {
        SUCCESS
    }
}