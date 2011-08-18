package com.bee32.sems.bom.service;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sems.bom.entity.Part;

public class BomCriteria
        extends CriteriaSpec {

    @LeftHand(Part.class)
    public static CriteriaElement active() {
        return isNull("obsolete");
    }

}
