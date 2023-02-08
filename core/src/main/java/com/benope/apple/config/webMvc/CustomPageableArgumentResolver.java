package com.benope.apple.config.webMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CustomPageableArgumentResolver implements PageableArgumentResolver {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Pageable resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String sortString = webRequest.getParameter("sort");

        Sort sort = Objects.nonNull(sortString)
                ? toSort(sortString)
                : Sort.unsorted();

        String rangeString = webRequest.getParameter("range");

        return Objects.nonNull(rangeString)
                ? toPageable(rangeString).withSort(sort)
                : Pageable.unpaged();
    }

    private Sort toSort(String sort) {
        List<String> pair = toStringPair(sort);

        String col = pair.get(0);
        String direction = pair.get(1);
        return Sort.by(Sort.Direction.valueOf(direction), col);
    }

    @SneakyThrows
    private List<String> toStringPair(String s) {
        List<String> pair = objectMapper.readValue(s, new TypeReference<>() {
        });

        String col = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, pair.get(0));
        String direction = Strings.toRootUpperCase(pair.get(1));

        return List.of(col, direction);
    }

    private PageRequest toPageable(String range) {
        List<Integer> pair = toIntegerPair(range);

        int from = pair.get(0);
        int to = pair.get(1);
        int size = to - from + 1;
        int page = from / size;

        return PageRequest.of(page, size);
    }

    @SneakyThrows
    private List<Integer> toIntegerPair(String range) {
        return objectMapper.readValue(range, new TypeReference<>() {
        });
    }

}
