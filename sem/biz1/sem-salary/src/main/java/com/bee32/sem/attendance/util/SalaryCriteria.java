package com.bee32.sem.attendance.util;

import java.util.Date;

import javax.free.Pair;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class SalaryCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listSalaryByYearAndMonth(int year, int month) {
        return new Equals("yearMonth", year * 100 + month);
    }

    public static ICriteriaElement listByDate(Date date) {
        Pair<Integer, Integer> yearAndMonth = SalaryDateUtil.getYearAndMonth(date);
        return new Equals("yearMonth", yearAndMonth.getFirst() * 100 + yearAndMonth.getSecond());
    }

}
