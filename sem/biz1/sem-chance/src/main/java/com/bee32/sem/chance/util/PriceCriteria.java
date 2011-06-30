package com.bee32.sem.chance.util;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class PriceCriteria {

    public static Criterion listByMaterial(String material) {
        return Restrictions.eq("material", material);
    }
}
