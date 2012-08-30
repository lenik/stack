package com.bee32.sem.hr.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.dto.JobPostDto;

public class JobPostVariableProvider
        extends AbstractSalaryVariableProvider {

    public static final String[] PARAMS = { "岗位补贴" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        if (variableName.equals("岗位补贴")) {
            EmployeeInfoDto employee = (EmployeeInfoDto) args.get(ARG_EMPLOYEE);
            JobPostDto role = employee.getRole();
            if (role == null)
                return BigDecimal.ZERO;
            else
                return role.getBonus();
        }
        return null;
    }

    @Override
    public String[] getVariableNames() {
        String[] variables = new String[PARAMS.length];
        int length = PARAMS.length;
        for (int i = 0; i < length; i++) {
            variables[i] = "@" + PARAMS[i];
        }
        return variables;
    }

    @Override
    public String getEntityType() {
        return this.getClass().getName();
    }
}
