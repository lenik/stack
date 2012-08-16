package com.bee32.sem.hr.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.JobTitle;

public class JobTitleVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        if (variableName.equals("职称补贴")) {
            EmployeeInfo employee = (EmployeeInfo) args.get(ARG_EMPLOYEE);
            JobTitle title = employee.getTitle();
            if (title == null)
                return BigDecimal.ZERO;
            else
                return title.getBonus();
        }
        return null;
    }

}
