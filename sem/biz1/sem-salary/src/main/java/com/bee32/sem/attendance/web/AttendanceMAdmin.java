package com.bee32.sem.attendance.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bee32.plover.orm.entity.IEntityAccessService;
import com.bee32.sem.attendance.dto.AttendanceMRDto;
import com.bee32.sem.attendance.entity.AttendanceMR;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.dto.BaseBonusDto;
import com.bee32.sem.salary.dto.SalaryDto;
import com.bee32.sem.salary.dto.SalaryItemDto;
import com.bee32.sem.salary.entity.BaseBonus;
import com.bee32.sem.salary.entity.Salary;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<AttendanceMRDto> attendanceMRList;
    List<EmployeeInfoDto> allEmployees;
    Date openDate = new Date();
    Date restrictionDate = new Date();
    Map<String, BaseBonusDto> bonusMap;

    public AttendanceMAdmin() {
        // current month -> AttendanceCriteria.getMonthList(new Date())
        super(AttendanceMR.class, AttendanceMRDto.class, 0);

        attendanceMRList = new ArrayList<AttendanceMRDto>();
        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        for (EmployeeInfoDto employee : allEmployees) {
            AttendanceMRDto monthRecord = new AttendanceMRDto();
            monthRecord.setEmployee(employee);
            attendanceMRList.add(monthRecord);
        }
    }

    public void inintAttendanceMR() {
        setOpenedObjects(attendanceMRList);
        attendanceMRList = new ArrayList<AttendanceMRDto>();
        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        for (EmployeeInfoDto employee : allEmployees) {
            AttendanceMRDto monthRecord = new AttendanceMRDto();
            monthRecord.setEmployee(employee);
            monthRecord.setShould_attendance(0.0);
            monthRecord.setReal_attendance(0.0);
            monthRecord.setTrip(0.0);
            monthRecord.setShould_overtime(0.0);
            monthRecord.setReal_overtime(0.0);
            attendanceMRList.add(monthRecord);
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
        List<AttendanceMR> existingList = DATA(AttendanceMR.class).list(AttendanceCriteria.getMonthList(openDate));
        Set<Long> idSet = new HashSet<Long>();
        for (AttendanceMR amr : existingList) {
            Long employeeId = amr.getEmployee().getId();
            idSet.add(employeeId);
        }

        List<AttendanceMR> entities = new ArrayList<AttendanceMR>();
        for (AttendanceMRDto dto : attendanceMRList) {
            Long employeeId = dto.getEmployee().getId();
            if (idSet.contains(employeeId))
                uiLogger.info(dto.getEmployee().getPerson().getName() + "  " + SalaryDateUtil.getMonNum(openDate)
                        + "月  考勤记录已经存在");
            else {
                dto.setDate(openDate);
                AttendanceMR mr = dto.unmarshal();
                entities.add(mr);
            }
        }
        try {
            if (entities.size() > 0) {
                DATA(AttendanceMR.class).saveAll(entities);
                uiLogger.info("添加考勤记录成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doCal(Object selection) {
        setSingleSelection(selection);
        openSelection();
        AttendanceMRDto mrDto = (AttendanceMRDto) getOpenedObject();
        SalaryDto salary = new SalaryDto().create();
        double total = 0.0;
        salary.setAttendance(mrDto);

        Map<String, BaseBonusDto> bonusMap = getBonusMap();
        List<SalaryItemDto> items = new ArrayList<SalaryItemDto>();

        // 基本工资
        SalaryItemDto base = new SalaryItemDto().create();
        base.setBonus(bonusMap.get("base").getBonus());
        base.setRate(mrDto.getReal_attendance() + mrDto.getReal_overtime() + mrDto.getTrip());
        base.setLabel("基本工资");

        salary.setBaseSalary(base);
        total += base.getRate() * base.getBonus().doubleValue();

        // TODO 小数点情况 || 全勤奖 perfect attendance award --> paa

        // 中餐补贴
        SalaryItemDto lunch = new SalaryItemDto();
        lunch.setBonus(bonusMap.get("lunch").getBonus());
        lunch.setAlternate(bonusMap.get("lunch").getLabel());
        lunch.setRate(mrDto.getReal_attendance());
        lunch.setLabel("午餐补贴");
        items.add(lunch);
        total += lunch.getRate() * lunch.getBonus().doubleValue();

        // 晚餐补贴
        SalaryItemDto supper = new SalaryItemDto();
        supper.setBonus(bonusMap.get("supper").getBonus());
        supper.setAlternate(bonusMap.get("supper").getLabel());
        supper.setRate(mrDto.getReal_overtime());
        supper.setLabel("晚餐补贴");
        items.add(supper);
        total += supper.getBonus().doubleValue() * supper.getRate();

        // 出差补贴
        SalaryItemDto trip = new SalaryItemDto();
        trip.setBonus(bonusMap.get("trip").getBonus());
        trip.setAlternate(bonusMap.get("trip").getLabel());
        trip.setRate(mrDto.getTrip());
        trip.setLabel("出差补贴");
        items.add(trip);
        total += trip.getBonus().doubleValue() * trip.getRate();

        // 职位补贴 ~~ 岗位
        SalaryItemDto jobPost = new SalaryItemDto();
        jobPost.setBonus(mrDto.getEmployee().getRole().getBonus());
        jobPost.setAlternate(mrDto.getEmployee().getRole().getName());
        jobPost.setRate(1);
        jobPost.setLabel("职位补贴");
        items.add(jobPost);
        total += jobPost.getBonus().doubleValue() * jobPost.getRate();

        // 学历补贴
        SalaryItemDto education = new SalaryItemDto();
        education.setBonus(mrDto.getEmployee().getEducation().getBonus());
        education.setAlternate(mrDto.getEmployee().getEducation().getName());
        education.setRate(1);
        education.setLabel("学历补贴");
        items.add(education);
        total += education.getBonus().doubleValue() * education.getRate();

        // 安全补贴
        if (mrDto.isSafety()) {
            SalaryItemDto safety = new SalaryItemDto();
            safety.setBonus(bonusMap.get("safety").getBonus());
            safety.setAlternate(bonusMap.get("safety").getLabel());
            safety.setRate(1);
            safety.setLabel("安全奖");
            items.add(safety);
            total += safety.getBonus().doubleValue() * safety.getRate();
        }

        int perfectAttendance;
        double rate = mrDto.getShould_attendance() + mrDto.getShould_overtime() - mrDto.getReal_attendance()
                - mrDto.getReal_overtime();
        if (rate <= 0)
            perfectAttendance = 300;
        else if (rate <= 1)
            perfectAttendance = 230;
        else if (rate <= 2)
            perfectAttendance = 160;
        else
            perfectAttendance = 0;

        SalaryItemDto paa = new SalaryItemDto();
        paa.setBonus(new BigDecimal(perfectAttendance));
        paa.setRate(1);
        paa.setLabel("全勤奖");
        items.add(paa);
        total += paa.getBonus().doubleValue() * paa.getRate();

        salary.setItems(items);
        salary.setDate(openDate);
        salary.setTotal(new BigDecimal(total));

        IEntityAccessService<Salary, Long> access = DATA(Salary.class);
        List<Salary> slist = access.list(AttendanceCriteria.getMonthRecordByEmployee(mrDto.getDate(), //
                mrDto.getEmployee().getId()));
        if (slist.size() == 0 || slist.isEmpty()) {
            try {
                access.saveOrUpdate(salary.unmarshal());
                mrDto.setCal(true);
                DATA(AttendanceMR.class).update(mrDto.unmarshal());
                uiLogger.info("生成工资条成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            uiLogger.warn("该月工资条已经存在");
    }

    Map<String, BaseBonusDto> getBonusMap() {
        if (bonusMap == null) {
            bonusMap = new HashMap<String, BaseBonusDto>();
            List<BaseBonusDto> all = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
            for (BaseBonusDto dto : all) {
//                bonusMap.put(dto.getId(), dto);
            }
        }
        return bonusMap;
    }

    public List<AttendanceMRDto> getAttendanceMRList() {
        return attendanceMRList;
    }

    public void setAttendanceMRList(List<AttendanceMRDto> list) {
        this.attendanceMRList = list;
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