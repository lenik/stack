package com.bee32.sem.attendance.util;

import java.util.Date;

import javax.free.Pair;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class EventBonusCriteria
        extends CriteriaSpec {

    public static ICriteriaElement getEventBonus(Date date, Long employeeId) {
        Pair<Date, Date> monthRange = SalaryDateUtil.toMonthRange(date);
        // TODO
        return null;
    }
}
