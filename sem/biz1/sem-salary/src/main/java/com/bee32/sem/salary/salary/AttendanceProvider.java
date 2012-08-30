package com.bee32.sem.salary.salary;

import java.math.BigDecimal;
import java.util.Date;

import com.bee32.plover.arch.util.TextMap;
import com.bee32.sem.api.AbstractSalaryVariableProvider;

public class AttendanceProvider
        extends AbstractSalaryVariableProvider {

    @Override
    public BigDecimal evaluate(TextMap args, String variableName) {
        Date beginDate = (Date) args.get(ARG_BEGIN_DATE);
        // 查询 月出勤记录，计算出出勤天数，
        return null;
    }

    @Override
    public String[] getVariableNames() {
        return null;
    }

    @Override
    public String getEntityType() {
        return this.getClass().getName();
    }
}
