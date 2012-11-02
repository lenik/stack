package com.bee32.sem.attendance.util;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class DefCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listEffectiveDef(Date begin, Date end) {
        CriteriaElement or = or(lessThan("beginTime", end), greaterOrEquals("endTime", begin));
        CriteriaElement and1 = and(lessThan("beginTime", end), equals("endTime", null));
        CriteriaElement and2 = and(lessThan("endTime", begin), equals("beginTime", null));

        return disj(or, and1, and2);
    }

}
