package com.bee32.sem.hr.web;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class EmployeeInfoCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listInfo(Date date) {
        CriteriaElement or = or(greaterThan("resignedDate", date), equals("resignedDate", null));
        return compose(alias("person", "person"), equals("person.employee", true), or);
    }

}
