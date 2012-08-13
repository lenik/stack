package com.bee32.sem.attendance.util;

import java.util.Date;
import java.util.Map;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.SqlRestriction;
import com.bee32.sem.wage.util.WageDateUtil;

public class AttendanceCriteria
        extends CriteriaSpec {
    public static ICriteriaElement getDayList(Date date) {
        Map<Integer, Date> map = WageDateUtil.toDayRange(date);
        return toRangeList(map);
    }

    public static ICriteriaElement getMonthList(Date date) {
        Map<Integer, Date> map = WageDateUtil.toMonthRange(date);
        return toRangeList(map);
    }

    static ICriteriaElement toRangeList(Map<Integer, Date> map) {
        return compose(and(greaterThan("date", map.get(0)), lessThan("date", map.get(1))));
    }

    public static ICriteriaElement getMonthRecordByEmployee(Date date, Long employeeId) {
        Map<Integer, Date> map = WageDateUtil.toMonthRange(date);
        return compose(and(greaterThan("date", map.get(0)), lessThan("date", map.get(1))),
                equals("employee.id", employeeId));
    }

    public static CriteriaElement getAttType(boolean attendance) {
        return equals("attendance", attendance);
    }

    public static ICriteriaElement getSum(Date date){
        return new SqlRestriction("");
    }

}
