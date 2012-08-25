package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<EmployeeInfoDto> allEmployees;
    Date openDate = new Date();
    Date restrictionDate = new Date();

    public AttendanceMAdmin() {
        // current month -> AttendanceCriteria.getMonthList(new Date())
        super(null, null, 0);

        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        for (EmployeeInfoDto employee : allEmployees) {
        }
    }

    public void inintAttendanceMR() {
        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        for (EmployeeInfoDto employee : allEmployees) {
        }
    }

    public void addMonthRestriction() {
        setSearchFragment("date", "限定" + SalaryDateUtil.getMonNum(restrictionDate) + "月出勤记录", //
                AttendanceCriteria.getMonthList(restrictionDate));
    }

    public void saveList() {
        /**
         * 本月已经存在考勤记录的月，将不再添加进去
         */
    }

    public void doCal(Object selection) {
        setSingleSelection(selection);
        openSelection();
        SalaryDto salary = new SalaryDto().create();
        double total = 0.0;
        List<SalaryElementDto> items = new ArrayList<SalaryElementDto>();

        // 基本工资
        SalaryElementDto base = new SalaryElementDto().create();
        base.setLabel("基本工资");


        // TODO 小数点情况 || 全勤奖 perfect attendance award --> paa

        // 中餐补贴
        SalaryElementDto lunch = new SalaryElementDto();
        lunch.setLabel("午餐补贴");
        items.add(lunch);

        // 晚餐补贴
        SalaryElementDto supper = new SalaryElementDto();
        supper.setLabel("晚餐补贴");
        items.add(supper);

        // 出差补贴
        SalaryElementDto trip = new SalaryElementDto();
        trip.setLabel("出差补贴");
        items.add(trip);

        // 岗位补贴 ~~ 岗位
        SalaryElementDto jobPost = new SalaryElementDto();
        jobPost.setLabel("岗位补贴");
        items.add(jobPost);

        // 学历补贴
        SalaryElementDto education = new SalaryElementDto();
        education.setLabel("学历补贴");
        items.add(education);

        SalaryElementDto paa = new SalaryElementDto();
        paa.setLabel("全勤奖");
        items.add(paa);

        IEntityAccessService<Salary, Long> access = DATA(Salary.class);
    }


    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getRestrictionDate() {
        return restrictionDate;
    }

    public void setRestrictionDate(Date restrictionDate) {
        this.restrictionDate = restrictionDate;
    }

}