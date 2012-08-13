package com.bee32.sem.wage.web;

import java.util.Date;

import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.wage.dto.SalaryDto;
import com.bee32.sem.wage.entity.Salary;
import com.bee32.sem.wage.util.WageDateUtil;

public class SalaryAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SalaryAdmin() {
        super(Salary.class, SalaryDto.class, 0);
    }

    Date targetDate;

    public void addDateRestriction() {
        setSearchFragment("date", "限定工资为" + WageDateUtil.getMonNum(targetDate) + "月份",
                AttendanceCriteria.getMonthList(targetDate));
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

}
