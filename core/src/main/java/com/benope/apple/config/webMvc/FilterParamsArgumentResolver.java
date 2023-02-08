package com.benope.apple.config.webMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class FilterParamsArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String FILTER_PARAM_NAME = "filter";

    private final ObjectMapper objectMapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FilterParams.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String filterString = Objects.requireNonNullElse(
                webRequest.getParameter(FILTER_PARAM_NAME),
                objectMapper.writeValueAsString(Map.of())
        );

        return objectMapper.readValue(filterString, parameter.getParameterType());
    }

}
