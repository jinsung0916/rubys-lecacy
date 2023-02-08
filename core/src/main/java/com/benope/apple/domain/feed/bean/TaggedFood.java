package com.benope.apple.domain.feed.bean;

import com.benope.apple.domain.food.bean.Food;
import com.benope.apple.utils.BeanUtil;
import com.benope.apple.utils.DomainObjectUtil;
import lombok.*;
import lombok.experimental.Delegate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tec.units.ri.quantity.Quantities;

import javax.measure.IncommensurableException;
import javax.measure.Unit;
import javax.measure.UnitConverter;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Slf4j
public class TaggedFood implements NutrientView {

    @Delegate
    private Food food;
    private Double userInput;

    @Builder
    protected TaggedFood(Long foodNo, Double userInput) {
        this.food = lazyLoadFood(foodNo);
        this.userInput = userInput;
    }

    private Food lazyLoadFood(Long foodNo) {
        EntityManager em = BeanUtil.getBean(EntityManager.class);
        return em.getReference(Food.class, foodNo);
    }

    public Double getUserInput() {
        return Objects.requireNonNullElse(userInput, 0D);
    }

    @Override
    public BigDecimal getTotalCalories() {
        BigDecimal calories = DomainObjectUtil.nullSafeEntityFunction(food, Food::getCalories);
        return Objects.nonNull(calories)
                ? calculateTotal(calories)
                : null;
    }

    @Override
    public BigDecimal getTotalFat() {
        BigDecimal fat = DomainObjectUtil.nullSafeEntityFunction(food, Food::getFat);
        return Objects.nonNull(fat)
                ? calculateTotal(fat)
                : null;
    }

    @Override
    public BigDecimal getTotalSaturatedFat() {
        BigDecimal saturatedFat = DomainObjectUtil.nullSafeEntityFunction(food, Food::getSaturatedFat);
        return Objects.nonNull(saturatedFat)
                ? calculateTotal(saturatedFat)
                : null;
    }

    @Override
    public BigDecimal getTotalCarbohydrate() {
        BigDecimal carbohydrate = DomainObjectUtil.nullSafeEntityFunction(food, Food::getCarbohydrate);
        return Objects.nonNull(carbohydrate)
                ? calculateTotal(carbohydrate)
                : null;
    }

    @Override
    public BigDecimal getTotalProtein() {
        BigDecimal protein = DomainObjectUtil.nullSafeEntityFunction(food, Food::getProtein);
        return Objects.nonNull(protein)
                ? calculateTotal(protein)
                : null;
    }

    private BigDecimal calculateTotal(BigDecimal decimal) {
        BigDecimal constant = constant();
        return Objects.nonNull(constant)
                ? constant.multiply(decimal)
                : null;
    }

    private BigDecimal constant() {
        try {
            UnitConverter converter = userInputConverter();
            BigDecimal userInputConverted = BigDecimal.valueOf(converter.convert(getUserInput()).doubleValue());
            BigDecimal nutrientStandards = DomainObjectUtil.nullSafeEntityFunction(food, Food::getNutrientStandards);
            return userInputConverted.divide(nutrientStandards, 2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            log.warn(toString(), e);
            return null;
        }
    }

    private UnitConverter userInputConverter() throws IncommensurableException {
        return userInputUnit().getConverterToAny(nutrientStandardUnit());
    }

    private Unit<?> userInputUnit() {
        String perServingUnitCd = StringUtils.toRootLowerCase(food.getPerServingUnitCd().name());
        return Quantities.getQuantity(getUserInput() + " " + perServingUnitCd).getUnit();
    }

    private Unit<?> nutrientStandardUnit() {
        String nutrientStandardUnitCd = StringUtils.toRootLowerCase(food.getNutrientStandardsUnitCd().name());
        return Quantities.getQuantity(food.getNutrientStandards() + " " + nutrientStandardUnitCd).getUnit();
    }

}
