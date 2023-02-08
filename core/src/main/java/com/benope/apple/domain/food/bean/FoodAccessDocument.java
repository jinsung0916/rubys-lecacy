package com.benope.apple.domain.food.bean;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(indexName = "food_access")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodAccessDocument {

    @Id
    private Long id;
    private Long foodNo;

    @Field(type = FieldType.Date_Nanos, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

}
