package com.bee32.sem.wage.web;

import java.math.BigDecimal;

import com.bee32.sem.attendance.dto.AttendanceMRDto;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.wage.dto.BaseBonusDto;
import com.bee32.sem.wage.dto.BaseSalaryDto;
import com.bee32.sem.wage.entity.BaseSalary;

public class SalaryAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public SalaryAdmin() {
        super(BaseSalary.class, BaseSalaryDto.class, 0);
    }

    public void calSalary(Object entry) {
        BigDecimal totle = new BigDecimal(0);
        BigDecimal base = new BigDecimal(2000);
        setSingleSelection(entry);
        openSelection();
        BaseSalaryDto salary = (BaseSalaryDto) getOpenedObject();
        AttendanceMRDto attendance = salary.getAttendance();
        EmployeeInfoDto employee = attendance.getEmployee();
        totle = totle.add(employee.getEducation().getBonus());
        System.out.println("学历补贴:" + employee.getEducation().getBonus());
        totle = totle.add(employee.getRole().getBonus());
        System.out.println("职位补贴 :" + employee.getRole().getBonus());
        totle = totle.add(employee.getTitle().getBonus());
        System.out.println("职称补贴 :" + employee.getTitle().getBonus());
        for (BaseBonusDto subsidy : salary.getSubsidies()) {
            totle = totle.add(subsidy.getBonus());
            System.out.println(subsidy.getLabel() + ":" + subsidy.getBonus());
        }
//        totle.setScale(4);
        System.out.println("totle::" + totle);

    }

}
