package com.bee32.sem.salary.web;

import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseEmployeeBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseEmployeeBean() {
        super(EmployeeInfo.class, EmployeeInfoDto.class, 0);
    }

}
