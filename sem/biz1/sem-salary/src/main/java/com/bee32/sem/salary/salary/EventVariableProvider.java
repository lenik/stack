package com.bee32.sem.salary.salary;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;
import com.bee32.sem.api.ISalaryVariableProvider;
import com.bee32.sem.attendance.util.EventBonusCriteria;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.entity.EventBonus;

/**
 * 事件参数提供器
 *
 * <p lang="en">
 * Event Variable Provider
 */
public class EventVariableProvider
        extends AbstractSalaryVariableProvider {

    String[] PARAMS = { "罚金", "奖金" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {

        EmployeeInfo employee = (EmployeeInfo) args.get(ISalaryVariableProvider.ARG_EMPLOYEE);
        Date begin = (Date) args.get(ISalaryVariableProvider.ARG_BEGIN_DATE);
        Date end = (Date) args.get(ISalaryVariableProvider.ARG_END_DATE);

        BigDecimal tmp = BigDecimal.ZERO;
        List<EventBonus> events;

        if (variableName.equals("罚金")) {
            events = DATA(EventBonus.class).list(EventBonusCriteria.listEvents(employee.getId(), begin, end, false));
            for (EventBonus event : events) {
                tmp = tmp.add(event.getBonus());
            }
            return tmp;
        }

        if (variableName.equals("奖金")) {
            events = DATA(EventBonus.class).list(EventBonusCriteria.listEvents(employee.getId(), begin, end, true));
            for (EventBonus event : events) {
                tmp = tmp.add(event.getBonus());
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
