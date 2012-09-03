package com.bee32.sem.attendance.util;

import java.util.Date;

import javax.free.Pair;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class DefCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listEffectiveDef(Date date) {
        Pair<Date, Date> range_m = SalaryDateUtil.toMonthRange(date);
        CriteriaElement or = or(lessThan("beginTime", range_m.getSecond()),
                greaterOrEquals("endTime", range_m.getFirst()));
        CriteriaElement and1 = and(lessThan("beginTime", range_m.getSecond()), equals("endTime", null));
        CriteriaElement and2 = and(lessThan("endTime", range_m.getFirst()), equals("beginTime", null));

        return disj(or, and1, and2);
    }

}
