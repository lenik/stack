package com.bee32.sems.bom.service;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sems.bom.entity.Component;

public class BomCriteria
        extends CriteriaSpec {

    @LeftHand(Component.class)
    public static CriteriaElement active() {
        return isNull("obsolete");
    }

}
