package com.bee32.sem.attendance.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.BaseBonusDto;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryElementDto;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<EmployeeInfoDto> allEmployees;
    Date openDate = new Date();
    Date restrictionDate = new Date();
    Map<String, BaseBonusDto> bonusMap;

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
        Map<String, BaseBonusDto> bonusMap = getBonusMap();
        List<SalaryElementDto> items = new ArrayList<SalaryElementDto>();

        // 基本工资
        SalaryElementDto base = new SalaryElementDto().create();
        base.setBonus(bonusMap.get("base").getBonus());
        base.setLabel("基本工资");

        total += base.getRate() * base.getBonus().doubleValue();

        // TODO 小数点情况 || 全勤奖 perfect attendance award --> paa

        // 中餐补贴
        SalaryElementDto lunch = new SalaryElementDto();
        lunch.setBonus(bonusMap.get("lunch").getBonus());
        lunch.setAlternate(bonusMap.get("lunch").getLabel());
        lunch.setLabel("午餐补贴");
        items.add(lunch);
        total += lunch.getRate() * lunch.getBonus().doubleValue();

        // 晚餐补贴
        SalaryElementDto supper = new SalaryElementDto();
        supper.setBonus(bonusMap.get("supper").getBonus());
        supper.setAlternate(bonusMap.get("supper").getLabel());
        supper.setLabel("晚餐补贴");
        items.add(supper);
        total += supper.getBonus().doubleValue() * supper.getRate();

        // 出差补贴
        SalaryElementDto trip = new SalaryElementDto();
        trip.setBonus(bonusMap.get("trip").getBonus());
        trip.setAlternate(bonusMap.get("trip").getLabel());
        trip.setLabel("出差补贴");
        items.add(trip);
        total += trip.getBonus().doubleValue() * trip.getRate();

        // 职位补贴 ~~ 岗位
        SalaryElementDto jobPost = new SalaryElementDto();
        jobPost.setRate(1);
        jobPost.setLabel("职位补贴");
        items.add(jobPost);
        total += jobPost.getBonus().doubleValue() * jobPost.getRate();

        // 学历补贴
        SalaryElementDto education = new SalaryElementDto();
        education.setRate(1);
        education.setLabel("学历补贴");
        items.add(education);
        total += education.getBonus().doubleValue() * education.getRate();

        SalaryElementDto paa = new SalaryElementDto();
        paa.setRate(1);
        paa.setLabel("全勤奖");
        items.add(paa);
        total += paa.getBonus().doubleValue() * paa.getRate();

        salary.setTotal(new BigDecimal(total));

        IEntityAccessService<Salary, Long> access = DATA(Salary.class);
    }

    Map<String, BaseBonusDto> getBonusMap() {
        if (bonusMap == null) {
            bonusMap = new HashMap<String, BaseBonusDto>();
            List<BaseBonusDto> all = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
            for (BaseBonusDto dto : all) {
// bonusMap.put(dto.getId(), dto);
            }
        }
        return bonusMap;
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