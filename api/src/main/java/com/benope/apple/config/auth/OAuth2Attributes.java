package com.benope.apple.config.auth;

import com.benope.apple.domain.member.bean.MemberAuth;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Attributes implements Serializable {

    private MemberAuth.MbAuthCd registrationId;
    private String nameAttributeKey;
    private Map<String, Object> attributes;

    private String userName;
    private String email;

    @Nullable
    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        String lowerCaseId = StringUtils.lowerCase(registrationId);
        if ("google".equals(lowerCaseId)) {
            return ofGoogle(userNameAttributeName, attributes);
        } else if ("naver".equals(lowerCaseId)) {
            return ofNaver(userNameAttributeName, attributes);
        } else if ("kakao".equals(lowerCaseId)) {
            return ofKakao(userNameAttributeName, attributes);
        } else if ("apple".equals(lowerCaseId)) {
            return ofApple(userNameAttributeName, attributes);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .registrationId(MemberAuth.MbAuthCd.GOOGLE)
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .userName(String.valueOf(attributes.get(userNameAttributeName)))
                .email(String.valueOf(attributes.get("email")))
                .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get(userNameAttributeName);

        return OAuth2Attributes.builder()
                .registrationId(MemberAuth.MbAuthCd.NAVER)
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .userName(String.valueOf(response.get("id")))
                .email(String.valueOf(response.get("email")))
                .build();
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2Attributes.builder()
                .registrationId(MemberAuth.MbAuthCd.KAKAO)
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .userName(String.valueOf(attributes.get(userNameAttributeName)))
                .email(String.valueOf(kakao_account.get("email")))
                .build();
    }

    private static OAuth2Attributes ofApple(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .registrationId(MemberAuth.MbAuthCd.APPLE)
                .nameAttributeKey(userNameAttributeName)
                .attributes(attributes)
                .userName(String.valueOf(attributes.get(userNameAttributeName)))
                .email(String.valueOf(attributes.get("email")))
                .build();
    }
}
