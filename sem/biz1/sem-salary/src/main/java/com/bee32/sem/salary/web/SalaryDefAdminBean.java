package com.bee32.sem.salary.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryElementDefDto;
import com.bee32.sem.salary.entity.SalaryElementDef;

public class SalaryDefAdminBean
        extends SimpleEntityViewBean {


    private static final long serialVersionUID = 1L;

    public SalaryDefAdminBean() {
        super(SalaryElementDef.class, SalaryElementDefDto.class, 0);
    }

}
