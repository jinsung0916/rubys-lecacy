package com.benope.apple.config.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Component
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {

    @NotBlank
    private String secret;
    private ValidityInSeconds validityInSeconds = ValidityInSeconds.EMPTY;

    @Getter
    @Setter
    public static class ValidityInSeconds {

        private static final ValidityInSeconds EMPTY = new ValidityInSeconds();

        @Min(1)
        private Long accessToken;
        @Min(1)
        private Long refreshToken;
    }

}
