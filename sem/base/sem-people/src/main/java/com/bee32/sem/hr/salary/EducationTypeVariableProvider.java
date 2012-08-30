package com.bee32.sem.hr.salary;

import java.math.BigDecimal;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.dto.PersonEducationTypeDto;

public class EducationTypeVariableProvider
        extends AbstractSalaryVariableProvider {

    public static final String[] PARAMS = { "学历补贴" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {

// PersonEducationType first = DATA(PersonEducationType.class).getFirst(//
// new Equals("label", educationLabel));
//
// if (first != null)
// return first.getBonus();
// else
// return null;

        if (variableName.equals("学历补贴")) {
            EmployeeInfoDto employee = (EmployeeInfoDto) args.get(ARG_EMPLOYEE);
            PersonEducationTypeDto education = employee.getEducation();
            if (education == null)
                return BigDecimal.ZERO;
            else
                return education.getBonus();
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
