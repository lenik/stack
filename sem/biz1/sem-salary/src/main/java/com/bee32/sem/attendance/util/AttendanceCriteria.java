package com.bee32.sem.attendance.util;

import java.util.Date;

import javax.free.Pair;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceCriteria
        extends CriteriaSpec {

    public static ICriteriaElement getDayList(Date date) {
        Pair<Date, Date> map = SalaryDateUtil.toDayRange(date);
        return toRangeList(map);
    }

    public static ICriteriaElement getMonthList(Date date) {
        Pair<Date, Date> map = SalaryDateUtil.toMonthRange(date);
        return toRangeList(map);
    }

    static ICriteriaElement toRangeList(Pair<Date, Date> map) {
        return conj(//
                greaterOrEquals("date", map.get(0)), //
                lessThan("date", map.get(1)));
    }

    public static ICriteriaElement getMonthRecordByEmployee(Date date, Long employeeId) {
        Pair<Date, Date> pair = SalaryDateUtil.toMonthRange(date);
        return conj(//
                greaterOrEquals("date", pair.get(0)), //
                lessThan("date", pair.get(1)), //
                equals("employee.id", employeeId));
    }

    public static ICriteriaElement listRecordByEmployee(Long employeeId, int yearMonth) {
        return and(equals("employee.id", employeeId), equals("yearMonth", yearMonth));
    }

    public static CriteriaElement getAttType(boolean attendance) {
        return equals("attendance", attendance);
    }

    public static CriteriaElement listByYearMonth(int yearMonth) {
        return equals("yearMonth", yearMonth);
    }

}
