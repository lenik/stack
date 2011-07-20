package com.bee32.sem.world.thing;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.QueryEntity;

public class UnitCriteria
        extends CriteriaSpec {

    @QueryEntity(Unit.class)
    public static final Criterion standardUnits = Restrictions.isNull("stdUnit");

}
