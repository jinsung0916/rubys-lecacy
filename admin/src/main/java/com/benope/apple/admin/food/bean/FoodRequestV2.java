package com.benope.apple.admin.food.bean;

import com.benope.apple.config.exception.BusinessException;
import com.benope.apple.config.validation.Create;
import com.benope.apple.config.webMvc.IdRequest;
import com.benope.apple.config.webMvc.RstCode;
import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.domain.food.bean.ProductStatusCd;
import com.benope.apple.domain.food.bean.UnitCd;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.benope.apple.domain.food.bean.UnitCd.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FoodRequestV2 implements IdRequest {

    private List<Long> id;

    @NotNull(groups = Create.class)
    private String foodNm;
    @NotNull(groups = Create.class)
    private String brand;

    @NotNull(groups = Create.class)
    private Long subCategoryNo;

    private String manufacturer;
    private BigDecimal carbohydrate;
    private BigDecimal sugars;
    private BigDecimal sugarAlcohol;
    private BigDecimal dietaryFiber;
    private BigDecimal allulose;
    private BigDecimal erythritol;
    private BigDecimal protein;
    private BigDecimal fat;
    private BigDecimal saturatedFat;
    private BigDecimal transFat;
    private BigDecimal nateulyum;
    private BigDecimal cholesterol;
    private BigDecimal calories;
    @NotNull(groups = Create.class)
    private BigDecimal weight;
    @NotNull(groups = Create.class)
    private UnitCd weightUnitCd;
    private BigDecimal perServing;
    @NotNull(groups = Create.class)
    private UnitCd perServingUnitCd;
    @NotNull(groups = Create.class)
    private BigDecimal nutrientStandards;
    @NotNull(groups = Create.class)
    private UnitCd nutrientStandardsUnitCd;
    @NotNull(groups = Create.class)
    private ProductStatusCd productStatusCd;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String storageType;
    private Long frontImageNo;
    @NotNull(groups = Create.class)
    @Length(min = 1)
    private String writer;

    private final List<UnitCd> grams = List.of(MG, G, KG);
    private final List<UnitCd> liters = List.of(ML, L);

    public Food toEntity(Long foodNo) {
        if ((Objects.nonNull(weightUnitCd) && Objects.nonNull(perServingUnitCd) && Objects.nonNull(nutrientStandardsUnitCd))
                && !(grams.contains(weightUnitCd) && grams.contains(perServingUnitCd) && grams.contains(nutrientStandardsUnitCd))
                && !(liters.contains(weightUnitCd) && liters.contains(perServingUnitCd) && liters.contains(nutrientStandardsUnitCd))) {
            throw new BusinessException(RstCode.UNIT_CD_NOT_MATCHED);
        }

        return Food.builder()
                .foodNo(foodNo)
                .foodNm(foodNm)
                .brand(brand)
                .foodCategoryNo(subCategoryNo)
                .manufacturer(manufacturer)
                .carbohydrate(carbohydrate)
                .sugars(sugars)
                .sugarAlcohol(sugarAlcohol)
                .dietaryFiber(dietaryFiber)
                .allulose(allulose)
                .erythritol(erythritol)
                .protein(protein)
                .fat(fat)
                .saturatedFat(saturatedFat)
                .transFat(transFat)
                .nateulyum(nateulyum)
                .cholesterol(cholesterol)
                .calories(calories)
                .weight(weight)
                .weightUnitCd(weightUnitCd)
                .perServing(perServing)
                .perServingUnitCd(perServingUnitCd)
                .nutrientStandards(nutrientStandards)
                .nutrientStandardsUnitCd(nutrientStandardsUnitCd)
                .productStatusCd(productStatusCd)
                .storageType(storageType)
                .frontImageNo(frontImageNo)
                .writer(writer)
                .build();
    }

}
