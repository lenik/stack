package com.bee32.sem.event.util;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class EventCriteria
        extends CriteriaSpec {

    public static CriteriaElement closed(Boolean closed) {
        return equals("closed", closed);
    }

    public static CriteriaElement beganFrom(Date date) {
        return greaterThan("beginTime", date);
    }

}
