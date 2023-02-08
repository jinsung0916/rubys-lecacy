package com.benope.apple.config.domain;

import org.springframework.context.ApplicationEvent;

public class BenopeEvent<T> extends ApplicationEvent {
    public BenopeEvent(T source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getSource() {
        return (T) super.getSource();
    }
}
