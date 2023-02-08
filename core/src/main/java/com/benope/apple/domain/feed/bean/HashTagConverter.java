package com.benope.apple.domain.feed.bean;

import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.webMvc.RstCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Converter
public class HashTagConverter implements AttributeConverter<List<String>, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final Pattern PATTERN = Pattern.compile("\\B(#.+\\b)(?!;)");

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return Objects.nonNull(attribute)
                ? writeValueAsString(attribute)
                : null;
    }

    @SneakyThrows
    private String writeValueAsString(List<String> attribute) {
        attribute.forEach(this::validateHashTag);
        return objectMapper.writeValueAsString(attribute);
    }

    private void validateHashTag(String hashtag) {
        Matcher matcher = PATTERN.matcher(hashtag);
        if (!matcher.matches()) {
            throw new BusinessException(RstCode.INVALID_JSON_TYPE);
        }
    }

    @SneakyThrows
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Objects.nonNull(dbData)
                ? readValue(dbData)
                : Collections.emptyList();
    }

    @SneakyThrows
    private List<String> readValue(String dbData) {
        String unwrapped = StringUtils.unwrap(dbData, "\"");
        String unescaped = StringEscapeUtils.unescapeJava(unwrapped);
        return objectMapper.readValue(unescaped, new TypeReference<>() {
        });
    }

}