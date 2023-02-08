package com.benope.apple.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;

@Profile("!test")
@Configuration
@EnableConfigurationProperties(FirebaseProperties.class)
@RequiredArgsConstructor
@Slf4j
public class FirebaseConfig {

    private final FirebaseProperties firebaseProperties;

    @PostConstruct
    public void initFirebase() {
        try {
            ClassPathResource serviceAccount = new ClassPathResource(firebaseProperties.getServiceAccountPath());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
