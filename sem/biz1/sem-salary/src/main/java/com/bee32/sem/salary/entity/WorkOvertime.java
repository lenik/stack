package com.bee32.sem.salary.entity;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.api.SalaryCalcContext;
import com.bee32.sem.api.SalaryElement;
import com.bee32.sem.hr.entity.EmployeeInfo;

public class WorkOvertime
        extends MomentInterval
        implements ISalaryVariableProvider {

    private static final long serialVersionUID = 1L;


    //TODO fix

    EmployeeInfo person;
    OvertimeType type;

    @Override
    public SalaryElementType[] getSalaryItems(SalaryCalcContext ctx) {
        SalaryElementType item = new SalaryElementType();
        switch(type.type){
        case 1:
            item.setPath("");
        }
        return null;
    }

}
