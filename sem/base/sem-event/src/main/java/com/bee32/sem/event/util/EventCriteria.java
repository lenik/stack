package com.bee32.sem.event.util;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class EventCriteria
        extends CriteriaSpec {

    public static CriteriaElement typeOf(String type) {
        if (type == null)
            return null;
        if (type.length() != 1)
            throw new IllegalArgumentException("Type is not a single char: " + type);
        // EventType eventType = EventType.valueOf(type.charAt(0));
        return equals("_type", type);
    }

    public static CriteriaElement closed(Boolean closed) {
        return _equals("closed", closed);
    }

    public static CriteriaElement beganFrom(Date date) {
        return _greaterThan("beginTime", date);
    }

}
