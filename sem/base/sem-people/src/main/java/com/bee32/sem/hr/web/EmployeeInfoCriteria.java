package com.bee32.sem.hr.web;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class EmployeeInfoCriteria extends CriteriaSpec {

    public static ICriteriaElement listInfo() {
        return compose(alias("person", "person"),
                equals("person.employee", true));
    }

}
