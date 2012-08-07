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
import com.bee32.sem.wage.dto.BaseBonusDto;
import com.bee32.sem.wage.dto.BaseSalaryDto;
import com.bee32.sem.wage.entity.BaseBonus;
import com.bee32.sem.wage.entity.BaseSalary;

public class AttendanceMAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<AttendanceMRDto> attendanceMRList;
    List<EmployeeInfoDto> allEmployees;
    Date openDate = new Date();
    Map<String, BaseBonusDto> bonusMap;

    public AttendanceMAdmin() {

        super(AttendanceMR.class, AttendanceMRDto.class, 0, AttendanceCriteria.getMonthList(new Date()));

        attendanceMRList = new ArrayList<AttendanceMRDto>();
        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        for (EmployeeInfoDto employee : allEmployees) {
            AttendanceMRDto monthRecord = new AttendanceMRDto();
            monthRecord.setEmployee(employee);
            monthRecord.setDate(new Date());
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
            monthRecord.setDate(new Date());
            monthRecord.setShould_attendance(0.0);
            monthRecord.setReal_attendance(0.0);
            monthRecord.setTrip(0.0);
            monthRecord.setShould_overtime(0.0);
            monthRecord.setReal_overtime(0.0);
            attendanceMRList.add(monthRecord);
        }
    }

    public void saveList() {
        // 判断记录是否已经存在
        /**
         * 本月已经存在考勤记录的月，将不再添加进去
         */
        List<AttendanceMR> existingList = ctx.data.access(AttendanceMR.class).list(
                AttendanceCriteria.getMonthList(openDate));
        Set<Long> idSet = new HashSet<Long>();
        for (AttendanceMR amr : existingList) {
            idSet.add(amr.getEmployee().getId());
        }

        List<AttendanceMR> entities = new ArrayList<AttendanceMR>();
        for (AttendanceMRDto dto : attendanceMRList) {
            if (!idSet.contains(dto.getId())) {
                AttendanceMR mr = dto.unmarshal();
                entities.add(mr);
            }
        }
        try {
            if (entities.size() > 0) {
                ctx.data.access(AttendanceMR.class).saveAll(entities);
                uiLogger.info("添加考勤记录成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

// public void vvvv() {
// List<EmployeeInfoDto> employees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
// for (EmployeeInfoDto employee : employees) {
// AttendanceMRDto attendance = new AttendanceMRDto();
// attendance.setEmployee(employee);
// attendance.setDate(new Date());
// attendanceMRList.add(attendance);
// }
// }
    public void doCal(Object selection) {
        setSingleSelection(selection);
        openSelection();
        AttendanceMRDto mrDto = (AttendanceMRDto) getOpenedObject();
        BaseSalaryDto salary = new BaseSalaryDto().create();
        salary.setAttendance(mrDto);
        Map<String, BaseBonusDto> bonusMap = getBonusMap();
        double dayBase = bonusMap.get("base").getBonus().doubleValue();
        double A = dayBase * (mrDto.getReal_attendance() + mrDto.getReal_overtime());
        double lunch = bonusMap.get("lunch").getBonus().doubleValue() * mrDto.getReal_attendance();
        double supper = bonusMap.get("supper").getBonus().doubleValue() * mrDto.getReal_overtime();
        double trip = bonusMap.get("trip").getBonus().doubleValue() * mrDto.getTrip();

        int rate = (int) (mrDto.getShould_attendance() + mrDto.getShould_overtime() - mrDto.getReal_attendance() - mrDto
                .getReal_overtime());
        int perfectAttendance;
        switch (rate) {
        case 0:
            perfectAttendance = 300;
            break;
        case 1:
            perfectAttendance = 230;
            break;
        case 2:
            perfectAttendance = 160;
            break;
        case 3:
            perfectAttendance = 100;
            break;
        default:
            perfectAttendance = 0;
        }

        double total = A + perfectAttendance + lunch + supper + trip;
        salary.setTotal(new BigDecimal(total));
        IEntityAccessService<BaseSalary, Long> access = ctx.data.access(BaseSalary.class);
        List<BaseSalary> slist = access.list(AttendanceCriteria.getMonthRecordByEmployee(mrDto.getDate(), //
                mrDto.getEmployee().getId()));
        if (slist.size() == 0 || slist.isEmpty()) {
            try {
                access.saveOrUpdate(salary.unmarshal());
                mrDto.setCal(true);
                ctx.data.access(AttendanceMR.class).update(mrDto.unmarshal());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            uiLogger.warn("该月工资条已经存在");

        System.out.println("*******************");
        System.out.println(total);
    }

    public void calSalary(Object selection) {
        setSingleSelection(selection);
        openSelection();

        Map<String, BaseBonusDto> map = new HashMap<String, BaseBonusDto>();
        List<BaseBonusDto> allBonuses = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
        for (BaseBonusDto bonus : allBonuses) {
            map.put(bonus.getName(), bonus);
        }

        List<BaseBonusDto> bonuses = new ArrayList<BaseBonusDto>();
        AttendanceMRDto attendanceMRDto = (AttendanceMRDto) getOpenedObject();
        BaseSalaryDto salary = new BaseSalaryDto();
        salary.setAttendance(attendanceMRDto);
        salary.setEmployee(attendanceMRDto.getEmployee());
        if (attendanceMRDto.getEmployee().isMotorist()) {
            bonuses.add(map.get("fuel"));
        }
        salary.setSubsidies(bonuses);
        salary.setDate(new Date());
        // BaseSalary entity = salary.unmarshal();
        try {
            IEntityAccessService<BaseSalary, Long> access = ctx.data.access(BaseSalary.class);
            List<BaseSalary> list = access.list(AttendanceCriteria.getMonthRecordByEmployee(new Date(),
                    attendanceMRDto.getId()));
            if (list.size() >= 0) {
                uiLogger.info("已经有该月工资条");
            } else {
                access.save(salary.unmarshal());
                uiLogger.info("生成工资条成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Map<String, BaseBonusDto> getBonusMap() {
        if (bonusMap == null) {
            bonusMap = new HashMap<String, BaseBonusDto>();
            List<BaseBonusDto> all = mrefList(BaseBonus.class, BaseBonusDto.class, 0);
            for (BaseBonusDto dto : all) {
                bonusMap.put(dto.getId(), dto);
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
}
