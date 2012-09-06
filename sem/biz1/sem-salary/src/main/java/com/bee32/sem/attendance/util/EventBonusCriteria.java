package com.bee32.sem.attendance.util;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class EventBonusCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listEvents(Long employeeId, Date begin, Date end, boolean award) {

        CriteriaElement goe = greaterOrEquals("beginTime", begin);
        CriteriaElement loe = lessOrEquals("beginTime", end);
        CriteriaElement employee_criteria = equals("employee.id", employeeId);

        if (award)
            return conj(goe, loe, employee_criteria, greaterOrEquals("bonus", 0));
        else
            return conj(goe, loe, employee_criteria, lessOrEquals("bonus", 0));
    }
}
