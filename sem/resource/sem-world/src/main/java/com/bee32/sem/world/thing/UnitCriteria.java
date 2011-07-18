package com.bee32.sem.world.thing;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.bee32.plover.criteria.hibernate.CriteriaTemplate;
import com.bee32.plover.criteria.hibernate.QueryEntity;

public class UnitCriteria
        extends CriteriaTemplate {

    @QueryEntity(Unit.class)
    public static final Criterion standardUnits = Restrictions.isNull("stdUnit");

}
