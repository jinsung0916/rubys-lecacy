package com.benope.apple.config.exception;

import io.sentry.Hint;
import io.sentry.SentryEvent;
import io.sentry.SentryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Component;

@ConditionalOnWebApplication
@Component
@RequiredArgsConstructor
public class CustomBeforeSendCallback implements SentryOptions.BeforeSendCallback {

    @Override
    public SentryEvent execute(SentryEvent event, Hint hint) {
        if (isBypass(event, hint)) {
            return null;
        }

        return event;
    }

    private boolean isBypass(SentryEvent event, Hint hint) {
        Throwable throwable = event.getThrowable();
        return throwable instanceof InsufficientAuthenticationException
                || throwable instanceof AccessDeniedException
                || throwable instanceof BusinessException;
    }

}
