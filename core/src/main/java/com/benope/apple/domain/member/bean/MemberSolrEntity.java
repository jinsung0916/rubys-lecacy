package com.benope.apple.domain.member.bean;


import com.benope.apple.config.domain.AbstractDocument;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "member")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSolrEntity extends AbstractDocument {

    @Id
    private Long id;
    @Indexed(type = "string", copyTo = {"_text_", "_text_str_"})
    private String nickname;
    @Indexed(type = "string")
    private String email;
    @Indexed(type = "string")
    private String phoneNumber_1;
    @Indexed(type = "string")
    private String phoneNumber_2;
    @Indexed(type = "string")
    private String phoneNumber_3;
    @Indexed(type = "plong")
    private Long profileImageNo;
    @Indexed(type = "string")
    private GenderCd genderCd;
    @Indexed(type = "string")
    private String birthday;
    @Indexed(type = "string")
    private String profileText;
    @Indexed(type = "string")
    private String accountLockedYn;
    @Indexed(type = "string")
    private String cafe24Id;
    @Indexed(type = "string")
    private ExpertCd expertCd;

    @Transient
    private @Setter Member member;

    public String getProfileImgUrl() {
        return DomainObjectUtil.nullSafeEntityFunction(this.member, Member::getProfileImgUrl);
    }

    public static MemberSolrEntity fromEntity(Member member) {
        return MemberSolrEntity.builder()
                .id(member.getMbNo())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phoneNumber_1(member.getPhoneNumber_1())
                .phoneNumber_2(member.getPhoneNumber_2())
                .phoneNumber_3(member.getPhoneNumber_3())
                .profileImageNo(member.getProfileImageNo())
                .genderCd(member.getGenderCd())
                .birthday(member.getBirthday())
                .profileText(member.getProfileText())
                .accountLockedYn(member.getAccountLockedYn())
                .cafe24Id(member.getCafe24Id())
                .expertCd(member.getExpertCd())
                .createdAt(member.getCreatedAt())
                .createdBy(member.getCreatedBy())
                .updatedAt(member.getUpdatedAt())
                .updatedBy(member.getUpdatedBy())
                .rowStatCd(member.getRowStatCd())
                .build();
    }

}