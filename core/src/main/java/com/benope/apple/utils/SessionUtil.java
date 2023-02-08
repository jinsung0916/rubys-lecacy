package com.benope.apple.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@Slf4j
public class SessionUtil {

    private static final String BATCH_PROPERTY = "spring.main.web-application-type";
    private static final String BATCH = "NONE";

    private static final Long BATCH_MB_NO = -1L;

    @Nullable
    public static Long getSessionMbNo() {
        Long mbNo = null;
        try {
            mbNo = doGetSessionMbNo();
        } catch (NumberFormatException e) {
            log.info("Unrecognized session attribute.", e);
        }
        return mbNo;
    }

    private static Long doGetSessionMbNo() {
        if (isBatch()) {
            return BATCH_MB_NO;
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return Objects.nonNull(authentication) ? Long.parseLong(authentication.getName()) : null;
        }
    }

    public static boolean isAuthenticated() {
        if (isBatch()) {
            return false;
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return Objects.nonNull(authentication) && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
        }
    }

    private static boolean isBatch() {
        Environment env = BeanUtil.getBean(Environment.class);
        return BATCH.equalsIgnoreCase(env.getProperty(BATCH_PROPERTY));
    }

}
