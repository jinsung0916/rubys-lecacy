package com.benope.apple.domain.member.bean;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.domain.image.bean.UploadImg;
import com.benope.apple.domain.member.event.MemberRegistEvent;
import com.benope.apple.domain.member.event.MemberUpdateEvent;
import com.benope.apple.utils.DateTimeUtil;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.Delegate;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.modelmapper.internal.util.Assert;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

import static com.benope.apple.config.domain.AbstractDomain.NOT_DELETED_CLAUSE;

@Entity
@Table(name = "mb")
@SQLDelete(sql = "UPDATE mb SET row_stat_cd = 'D' WHERE mb_no = ? AND opt_lock = ?")
@Where(clause = NOT_DELETED_CLAUSE)
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractDomain {

    @Transient
    public static final Member NULL_MEMBER =
            Member.builder()
                    .nickname("탈퇴한 회원")
                    .build();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mb_no")
    private Long mbNo;
    private String nickname;
    private String email;
    private String phoneNumber_1;
    private String phoneNumber_2;
    private String phoneNumber_3;
    @Column(name = "profile_image_no")
    private Long profileImageNo;
    @Enumerated(EnumType.STRING)
    private GenderCd genderCd;
    private String birthday;
    private String profileText;
    private String accountLockedYn;
    @Column(name = "cafe24_id")
    private String cafe24Id;
    private LocalDate quitDate;
    @Enumerated(EnumType.STRING)
    private @Setter ExpertCd expertCd;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "profile_image_no",
            referencedColumnName = "img_no",
            insertable = false,
            updatable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
    )
    private UploadImg profileImg;

    @Embedded
    @Delegate
    private MemberCollectionContainer container = new MemberCollectionContainer();

    public String getProfileImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.profileImg, UploadImg::getImgFileUrl);
    }

    public Member toQuitMember() {
        return Member.builder()
                .mbNo(this.mbNo)
                .quitDate(DateTimeUtil.getCurrentDate())
                .build();
    }

    @Override
    protected void beforeCreate() {
        validate();

        this.accountLockedYn = "N";

        registerEvent(new MemberRegistEvent(this));
    }

    @Override
    protected void beforeUpdate() {
        validate();

        registerEvent(new MemberUpdateEvent(this));
    }

    private void validate() {
        Assert.notNull(nickname);

        if (Objects.nonNull(container)) {
            container.validate();
        }
    }

    @Override
    public String getName() {
        return String.valueOf(this.mbNo);
    }

}
