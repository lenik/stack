package com.bee32.sem.hr.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.JobPost;

public class JobPostVariableProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        if (variableName.equals("岗位津贴")) {
            EmployeeInfo employee = (EmployeeInfo) args.get(ARG_EMPLOYEE);
            JobPost role = employee.getRole();
            if (role == null)
                return BigDecimal.ZERO;
            else
                return role.getBonus();
        }
        return null;
    }

}
