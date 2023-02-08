package com.benope.apple.domain.theme.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Converter
public class ThemeConditionConverter implements AttributeConverter<List<ThemeCondition>, String> {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<ThemeCondition> attribute) {
        return Objects.nonNull(attribute)
                ? writeValueAsString(attribute)
                : null;
    }

    @SneakyThrows
    private static String writeValueAsString(List<ThemeCondition> attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @Override
    public List<ThemeCondition> convertToEntityAttribute(String dbData) {
        return Objects.nonNull(dbData)
                ? readValue(dbData)
                : Collections.emptyList();
    }

    @SneakyThrows
    private List<ThemeCondition> readValue(String dbData) {
        String unwrapped = StringUtils.unwrap(dbData, "\"");
        String unescaped = StringEscapeUtils.unescapeJava(unwrapped);
        return objectMapper.readValue(unescaped, new TypeReference<>() {
        });
    }

}
