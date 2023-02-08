package com.benope.apple.utils;


import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class EntityManagerWrapper {

    @Delegate
    private final EntityManager em;

    public <T> T flushAndRefresh(T entity) {
        em.flush();
        em.refresh(em.contains(entity) ? entity : em.merge(entity));
        return entity;
    }

}
