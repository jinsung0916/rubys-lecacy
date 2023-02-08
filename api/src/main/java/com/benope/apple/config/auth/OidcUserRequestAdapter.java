package com.benope.apple.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class OidcUserRequestAdapter implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private static final String USERNAME_ATTR = "USERNAME";

    private final CustomUserDetailsService userDetailsService;

    private final OidcUserService delegate = new OidcUserService();

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = delegate.loadUser(userRequest);

        log.info(oidcUser.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oidcUser.getAttributes());

        UserDetails userDetails = userDetailsService.loadUser(oAuth2Attributes.getRegistrationId(), oAuth2Attributes.getUserName());

        return new MockOidcUser(
                userDetails.getAuthorities(),
                Map.of(USERNAME_ATTR, userDetails.getUsername()),
                USERNAME_ATTR
        );
    }


    public static class MockOidcUser extends DefaultOAuth2User implements OidcUser {

        public MockOidcUser(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
            super(authorities, attributes, nameAttributeKey);
        }

        @Override
        public Map<String, Object> getClaims() {
            throw new UnsupportedOperationException();
        }

        @Override
        public OidcUserInfo getUserInfo() {
            throw new UnsupportedOperationException();
        }

        @Override
        public OidcIdToken getIdToken() {
            throw new UnsupportedOperationException();
        }

    }


}
