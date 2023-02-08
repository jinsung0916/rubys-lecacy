package com.benope.apple.domain.feed.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
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
public class UploadImgFoodRelationConverter implements AttributeConverter<List<UploadImgFoodRelation>, String> {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<UploadImgFoodRelation> attribute) {
        return Objects.nonNull(attribute)
                ? objectMapper.writeValueAsString(attribute)
                : null;
    }

    @Override
    public List<UploadImgFoodRelation> convertToEntityAttribute(String dbData) {
        return Objects.nonNull(dbData)
                ? readValue(dbData)
                : Collections.emptyList();
    }

    @SneakyThrows
    private List<UploadImgFoodRelation> readValue(String dbData) {
        String unwrapped = StringUtils.unwrap(dbData, "\"");
        String unescaped = StringEscapeUtils.unescapeJava(unwrapped);
        return objectMapper.readValue(unescaped, new TypeReference<>() {
        });
    }

}