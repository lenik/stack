package com.bee32.sem.inventory.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.material.entity.StockWarehouse;

public class StockWarehouseCriteria extends CriteriaSpec {

    @LeftHand(StockWarehouse.class)
    public static CriteriaElement addressLike(String pattern) {
        if (pattern == null || pattern.isEmpty())
            return null;
        return likeIgnoreCase("address", pattern, MatchMode.ANYWHERE);
    }

    @LeftHand(StockWarehouse.class)
    public static CriteriaElement phoneLike(String pattern) {
        if (pattern == null || pattern.isEmpty())
            return null;
        return likeIgnoreCase("phone", pattern, MatchMode.ANYWHERE);
    }

}
