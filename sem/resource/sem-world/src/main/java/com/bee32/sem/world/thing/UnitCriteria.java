package com.bee32.sem.world.thing;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;

public class UnitCriteria
        extends CriteriaSpec {

    @LeftHand(Unit.class)
    public static final CriteriaElement standardUnits = isNull("stdUnit");

}
