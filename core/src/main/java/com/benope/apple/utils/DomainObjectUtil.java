package com.benope.apple.utils;

import com.benope.apple.config.domain.AbstractDomain;
import com.benope.apple.config.domain.RowStatCd;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public class DomainObjectUtil {

    public static <T> Stream<T> nullSafeStream(Collection<T> c) {
        return Objects.nonNull(c) ? c.stream() : Stream.empty();
    }

    public static <T, R> R nullSafeFunction(T obj, Function<T, R> function) {
        return Objects.nonNull(obj) ? function.apply(obj) : null;
    }

    public static <T extends AbstractDomain, R> R nullSafeEntityFunction(T obj, Function<T, R> function) {
        try {
            return !isDeleted(obj)
                    ? nullSafeFunction(obj, function)
                    : null;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private static <T extends AbstractDomain> boolean isDeleted(T obj) {
        RowStatCd rowStatCd = nullSafeFunction(obj, AbstractDomain::getRowStatCd);
        return RowStatCd.D.equals(rowStatCd);
    }

}
