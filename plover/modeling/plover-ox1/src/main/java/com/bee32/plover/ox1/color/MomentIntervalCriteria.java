package com.bee32.plover.ox1.color;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;

public class MomentIntervalCriteria
        extends CriteriaSpec {

    @LeftHand(MomentInterval.class)
    public static CriteriaElement timeBetween(Date start, Date end) {
        if (start == null && end == null)
            return null;
        if (start == null)
            return lessOrEquals("endTime", end);
        if (end == null)
            return greaterOrEquals("beginTime", start);
        return between("beginTime", start, end);
    }

}
