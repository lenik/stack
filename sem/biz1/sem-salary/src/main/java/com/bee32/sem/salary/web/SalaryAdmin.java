package com.bee32.sem.salary.web;

import java.util.Date;

import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class SalaryAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SalaryAdmin() {
        super(Salary.class, SalaryDto.class, 0);
    }

    Date targetDate;

    public void addDateRestriction() {
        setSearchFragment("date", "限定工资为" + SalaryDateUtil.getMonNum(targetDate) + "月份",
                AttendanceCriteria.getMonthList(targetDate));
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }



}
