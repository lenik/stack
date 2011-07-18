package com.bee32.sem.chance.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.criteria.hibernate.CriteriaTemplate;

public class PriceCriteria
        extends CriteriaTemplate {

    public static Criterion listByMaterial(String material) {
        return Restrictions.eq("material", material);
    }

    public static Criterion listBasePriceByMaterial(String material) {
        return Restrictions.like("material", "%" + material + "%");
    }

    public static Criterion currentBasePrice(String material) {
        return Restrictions.eq("material", material);
    }

    public static Criterion listQuotationByChance(Long chanceId) {
        return Restrictions.eq("chance.id", chanceId);
    }
}
