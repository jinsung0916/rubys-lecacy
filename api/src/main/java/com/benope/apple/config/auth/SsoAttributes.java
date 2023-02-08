package com.benope.apple.config.auth;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SsoAttributes implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SSO_ATTRIBUTES_REQUEST_COOKIE_NAME = "sso_attributes";

    private ClientId clientId;
    private String clientSecret;
    private String redirectUri;
    private String state;

}
