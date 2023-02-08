package com.benope.apple.config.firebase;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "firebase")
@ConstructorBinding
@Getter
@RequiredArgsConstructor
public class FirebaseProperties {
    private final String serviceAccountPath;
}
