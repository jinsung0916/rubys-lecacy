package com.benope.apple.domain.ranking.bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class FoodRankingDataConverter implements AttributeConverter<FoodRankingData, String> {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    @Override
    public String convertToDatabaseColumn(FoodRankingData attribute) {
        return Objects.nonNull(attribute)
                ? writeValueAsString(attribute)
                : null;
    }

    @SneakyThrows
    private static String writeValueAsString(FoodRankingData attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @Override
    public FoodRankingData convertToEntityAttribute(String dbData) {
        return Objects.nonNull(dbData)
                ? readValue(dbData)
                : null;
    }

    @SneakyThrows
    public static FoodRankingData readValue(String dbData) {
        String unwrapped = StringUtils.unwrap(dbData, "\"");
        String unescaped = StringEscapeUtils.unescapeJava(unwrapped);
        return objectMapper.readValue(unescaped, new TypeReference<>() {
        });
    }

}
