package com.bee32.sem.salary.entity;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.api.ISalaryProvider;
import com.bee32.sem.api.SalaryCalcContext;
import com.bee32.sem.api.SalaryElement;
import com.bee32.sem.hr.entity.EmployeeInfo;

public class WorkOvertime
        extends MomentInterval
        implements ISalaryProvider {

    private static final long serialVersionUID = 1L;


    //TODO fix

    EmployeeInfo person;
    OvertimeType type;

    @Override
    public SalaryElement[] getSalaryItems(SalaryCalcContext ctx) {
        SalaryElement item = new SalaryElement();
        switch(type.type){
        case 1:
            item.setPath("");
        }
        return null;
    }

}
