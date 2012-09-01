package com.bee32.sem.salary.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.hr.entity.EmployeeInfo;

public class BaseSalaryProvider
        extends AbstractSalaryVariableProvider {

    String[] PARAMS = { "基础工资" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        if (variableName.equals("基础工资")) {
            EmployeeInfo employee = (EmployeeInfo) args.get(ISalaryVariableProvider.ARG_EMPLOYEE);
            return employee.getBaseSalary();
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
