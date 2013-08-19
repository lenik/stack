package com.bee32.sem.salary.salary;

import java.math.BigDecimal;
import java.util.List;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.hr.entity.PersonSkill;
import com.bee32.sem.salary.util.SalaryDateUtil;

/**
 * 雇员参数提供器
 *
 * <p lang="en">
 * Employee Variable Provider
 */
public class EmployeeVariableProvider
        extends AbstractSalaryVariableProvider {

    String[] PARAMS = { "基础工资", "养老金", "工作年限", "技能补贴" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        EmployeeInfo employee = (EmployeeInfo) args.get(ISalaryVariableProvider.ARG_EMPLOYEE);
        if (variableName.equals("基础工资"))
            return employee.getBaseSalary();
        if (variableName.equals("养老金"))
            return employee.getPension();
        if (variableName.equals("工作年限")) {
            int month = SalaryDateUtil.getFixedNumberOfYears(employee.getEmployedDate());
            return new BigDecimal(month);
        }
        if (variableName.equals("技能补贴")) {
            List<PersonSkill> skills = employee.getSkills();
            BigDecimal tmp = BigDecimal.ZERO;
            for (PersonSkill skill : skills) {
                tmp = tmp.add(skill.getBonus());
            }
            return tmp;
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

}
