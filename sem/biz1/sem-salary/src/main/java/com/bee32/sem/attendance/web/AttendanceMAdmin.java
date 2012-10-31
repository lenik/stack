package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.attendance.dto.AttendanceMRecordDto;
import com.bee32.sem.attendance.entity.AttendanceMRecord;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.attendance.util.AttendanceCriteria;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceMAdmin
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    List<EmployeeInfoDto> allEmployees;
    Date openDate = new Date();
    AttendanceMRecordDto editingAttendance;

    public AttendanceMAdmin() {
        super(AttendanceMRecord.class, AttendanceMRecordDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {

        int yearMonth = SalaryDateUtil.convertToYearMonth(openDate);
        elements.add(AttendanceCriteria.listByYearMonth(yearMonth));

    }

    /**
     * generator & save default attendanceData
     */
    public void inintAttendanceMR() {
        List<EmployeeInfo> employees = DATA(EmployeeInfo.class).list();

        List<AttendanceMRecord> entityList = new ArrayList<AttendanceMRecord>();
        int yearMonth = SalaryDateUtil.convertToYearMonth(openDate);

        for (EmployeeInfo employee : employees) {
            if (isMonthRecordExists(employee.getId(), yearMonth))
                uiLogger.warn(employee.getPerson().getDisplayName() + "『" + //
                        yearMonth / 100 + "年" + yearMonth % 100 + "月』 出勤记录 已经存在！");
            else {
                AttendanceMRecord record = new AttendanceMRecord();
                record.setEmployee(employee);
                record.setYearMonth(yearMonth);
                record.setSafe(true);
                record.setAttendanceData(AttendanceMRecordDto.generatorDefaultAttendanceData(yearMonth / 100,
                        yearMonth % 100));
                entityList.add(record);
            }
        }

        if (entityList.size() > 0) {
            try {
                DATA(AttendanceMRecord.class).saveAll(entityList);
                refreshData();
                uiLogger.info("默认出勤记录生成完毕！");
            } catch (Exception e) {
                e.printStackTrace();
                uiLogger.error("生成默认出勤记录发生故障", e);
            }

        } else
            uiLogger.error("没有需要添加的出勤记录");

    }

    @Override
    protected boolean preUpdate(UnmarshalMap uMap)
            throws Exception {
        AttendanceMRecordDto dto = (AttendanceMRecordDto) getOpenedObject();
        boolean existing = isMonthRecordExists(dto.getEmployee().getId(), SalaryDateUtil.convertToYearMonth(openDate));
        if (existing && isCreating()) {
            uiLogger.error("『" + dto.getEmployee().getPersonName() + "』" + dto.getYearMonthString() + "出勤记录已经存在！");
            return false;
        }
        return super.preUpdate(uMap);
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

    @Operation
    public void showCreateForm() {
        int yearMonth = SalaryDateUtil.convertToYearMonth(openDate);
        AttendanceMRecordDto instance = new AttendanceMRecordDto();
        instance.setYear(yearMonth / 100);
        instance.setMonth(yearMonth % 100);
        instance.setAttendanceData(AttendanceMRecordDto
                .generatorDefaultAttendanceData(yearMonth / 100, yearMonth % 100));
        setOpenedObject(instance);
        showView(StandardViews.CREATE_FORM);
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
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