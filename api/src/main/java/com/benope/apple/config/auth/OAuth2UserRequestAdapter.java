package com.benope.apple.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@RequiredArgsConstructor
public class OAuth2UserRequestAdapter implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String USERNAME_ATTR = "USERNAME";

    private final CustomUserDetailsService userDetailsService;

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserDetails userDetails = userDetailsService.loadUser(oAuth2Attributes.getRegistrationId(), oAuth2Attributes.getUserName());

        return new DefaultOAuth2User(
                userDetails.getAuthorities(),
                Map.of(USERNAME_ATTR, userDetails.getUsername()),
                USERNAME_ATTR
        );
    }

}
