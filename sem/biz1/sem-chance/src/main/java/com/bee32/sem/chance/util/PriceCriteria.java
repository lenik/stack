package com.bee32.sem.chance.util;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class PriceCriteria
        extends CriteriaSpec {

    public static CriteriaElement listByMaterial(String material) {
        return equals("material", material);
    }

    public static CriteriaElement listBasePriceByMaterial(String material) {
        return like("material", "%" + material + "%");
    }

    public static CriteriaElement currentBasePrice(String material) {
        return equals("material", material);
    }

    public static CriteriaElement listQuotationByChance(Long chanceId) {
        return equals("chance.id", chanceId);
    }
}
