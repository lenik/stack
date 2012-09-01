package com.bee32.sem.attendance.util;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class SalaryCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listSalaryByYearAndMonth(int year, int month) {
        return new Equals("yearMonth", year * 100 + month);
    }

}
