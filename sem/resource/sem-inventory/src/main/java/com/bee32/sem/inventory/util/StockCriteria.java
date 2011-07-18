package com.bee32.sem.inventory.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.criteria.hibernate.CriteriaTemplate;
import com.bee32.plover.criteria.hibernate.QueryEntity;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.entity.StockSnapshot;

public class StockCriteria
        extends CriteriaTemplate {

    @QueryEntity(StockOrder.class)
    public static Criterion baseOf(StockSnapshot snapshot) {
        return Restrictions.eq("base", snapshot);
    }

    public static Criterion ancestorOf(StockSnapshot snapshot) {
        List<?> list = new ArrayList<E>();

        StockSnapshot node = snapshot;
        while (node != null) {
            node = node.getParent();
        }

        return Restrictions.in("base", list);
    }

}
