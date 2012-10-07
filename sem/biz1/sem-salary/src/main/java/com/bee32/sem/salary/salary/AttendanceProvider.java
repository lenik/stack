package com.bee32.sem.salary.salary;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;

public class AttendanceProvider
        extends AbstractSalaryVariableProvider {

    String[] PARAMS = { "内勤天数", "外勤天数", "应出勤天数", "加班天数", "应加班天数" };

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        Date beginDate = (Date) args.get(ARG_BEGIN_DATE);
        // 查询 月出勤记录，计算出出勤天数，
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
