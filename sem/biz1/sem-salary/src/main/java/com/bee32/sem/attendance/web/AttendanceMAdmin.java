package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.sem.attendance.dto.AttendanceMRecordDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<EmployeeInfoDto> allEmployees;
    Date openDate = new Date();
    List<AttendanceMRecordDto> attendances;
    AttendanceMRecordDto editingAttendance;

    public AttendanceMAdmin() {
        super(AttendanceMRecord.class, AttendanceMRecordDto.class, 0, AttendanceCriteria.listByYearMonth( //
                SalaryDateUtil.convertToYearMonth(new Date())));

        inintAttendanceMR();
    }

    public void inintAttendanceMR() {
        attendances = new ArrayList<AttendanceMRecordDto>();
        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        int yearMonth = SalaryDateUtil.convertToYearMonth(openDate);

        for (int i = 0; i < allEmployees.size(); i++) {

            AttendanceMRecordDto attendance = new AttendanceMRecordDto();
            attendance.setTmpId(i);
            attendance.setEmployee(allEmployees.get(i));
            attendance.setSafe(true);
            attendance.setRecords(//
                    AttendanceMRecordDto.generatorDefaultAttendanceData(yearMonth / 100, yearMonth % 100));
            attendance.setAttendanceData(//
                    AttendanceMRecordDto.generatorDefaultAttendanceData(yearMonth / 100, yearMonth % 100));
            attendance.setYear(yearMonth / 100);
            attendance.setMonth(yearMonth % 100);

            attendances.add(attendance);
        }
    }

    public void showEditingAttendance(AttendanceMRecordDto editingAttendance) {
        this.editingAttendance = editingAttendance;
    }

    public void test() {
        attendances.set(editingAttendance.getTmpId(), editingAttendance);
    }

    public void saveBatchAttendance() {

        List<AttendanceMRecord> entities = new ArrayList<AttendanceMRecord>();

        for (AttendanceMRecordDto dto : attendances) {
            try {
                AttendanceMRecord entity = dto.unmarshal();
                int yearMonth = entity.getYearMonth();
                if (isMonthRecordExists(entity.getEmployee().getId(), yearMonth))
                    uiLogger.warn(dto.getEmployee().getPersonName() + "『" + //
                            yearMonth / 100 + "年" + yearMonth % 100 + "月』 出勤记录 已经存在！");
                else
                    entities.add(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (entities.size() > 0) {
            DATA(AttendanceMRecord.class).saveAll(entities);
        } else {
            uiLogger.warn("没有需要添加的出勤记录");
        }

    }

    /**
     * return true if entity exists
     */
    static boolean isMonthRecordExists(Long employeeId, int yearMonth) {
        List<AttendanceMRecord> records = DATA(AttendanceMRecord.class).list(
                AttendanceCriteria.listRecordByEmployee(employeeId, yearMonth));
        if (records.size() > 0)
            return true;
        return false;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public SelectableList<AttendanceMRecordDto> getAttendances() {
        return SelectableList.decorate(attendances);
    }

    public void setAttendances(List<AttendanceMRecordDto> attendances) {
        this.attendances = attendances;
    }

    public AttendanceMRecordDto getEditingAttendance() {
        return editingAttendance;
    }

    public void setEditingAttendance(AttendanceMRecordDto editingAttendance) {
        this.editingAttendance = editingAttendance;
    }

    public List<AttendanceType> getAttendanceTypes() {
        return new ArrayList<AttendanceType>(AttendanceType.values());
    }
}