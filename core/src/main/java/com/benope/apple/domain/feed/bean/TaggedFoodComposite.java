package com.benope.apple.domain.feed.bean;

import com.benope.apple.utils.DomainObjectUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class TaggedFoodComposite implements NutrientView {

    private final List<TaggedFood> tags;

    @Override
    public BigDecimal getTotalCalories() {
        boolean nonNull = DomainObjectUtil.nullSafeStream(tags).map(TaggedFood::getTotalCalories).noneMatch(Objects::isNull);

        return nonNull
                ? DomainObjectUtil.nullSafeStream(tags)
                .map(NutrientView::getTotalCalories)
                .reduce(BigDecimal::add)
                .orElse(null)
                : null;
    }

    @Override
    public BigDecimal getTotalFat() {
        boolean nonNull = DomainObjectUtil.nullSafeStream(tags).map(TaggedFood::getTotalFat).noneMatch(Objects::isNull);

        return nonNull
                ? DomainObjectUtil.nullSafeStream(tags)
                .map(NutrientView::getTotalFat)
                .reduce(BigDecimal::add)
                .orElse(null)
                : null;
    }

    @Override
    public BigDecimal getTotalSaturatedFat() {
        boolean nonNull = DomainObjectUtil.nullSafeStream(tags).map(TaggedFood::getTotalSaturatedFat).noneMatch(Objects::isNull);

        return nonNull
                ? DomainObjectUtil.nullSafeStream(tags)
                .map(NutrientView::getTotalSaturatedFat)
                .reduce(BigDecimal::add)
                .orElse(null)
                : null;
    }

    @Override
    public BigDecimal getTotalCarbohydrate() {
        boolean nonNull = DomainObjectUtil.nullSafeStream(tags).map(TaggedFood::getTotalCarbohydrate).noneMatch(Objects::isNull);

        return nonNull
                ? DomainObjectUtil.nullSafeStream(tags)
                .map(NutrientView::getTotalCarbohydrate)
                .reduce(BigDecimal::add)
                .orElse(null)
                : null;
    }

    @Override
    public BigDecimal getTotalProtein() {
        boolean nonNull = DomainObjectUtil.nullSafeStream(tags).map(TaggedFood::getTotalProtein).noneMatch(Objects::isNull);

        return nonNull
                ? DomainObjectUtil.nullSafeStream(tags)
                .map(NutrientView::getTotalProtein)
                .reduce(BigDecimal::add)
                .orElse(null)
                : null;
    }

}
