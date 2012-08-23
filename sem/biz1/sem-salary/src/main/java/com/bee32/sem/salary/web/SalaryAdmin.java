package com.bee32.sem.salary.web;

import java.util.Date;

import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.frame.ui.ELListMBean;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class SalaryAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SalaryAdmin() {
        super(Salary.class, SalaryDto.class, SalaryDto.ELEMENTS);
    }

    Date targetDate;
    SalaryElementDto selectedElement = new SalaryElementDto().create();

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

    public SalaryElementDto getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(SalaryElementDto selectedElement) {
        this.selectedElement = selectedElement;
    }

    final ListMBean<SalaryElementDto> salaryElementMBean = ELListMBean.fromEL(this,//
            "openedObject.elements", SalaryElementDto.class);

    public ListMBean<SalaryElementDto> getSalaryElementMBean() {
        return salaryElementMBean;
    }

}
