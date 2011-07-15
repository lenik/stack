package com.bee32.sem.world.thing;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class UnitCriteria {

    /**
     * @see Unit
     */
    public static final Criterion standardUnits = Restrictions.isNull("stdUnit");

}
