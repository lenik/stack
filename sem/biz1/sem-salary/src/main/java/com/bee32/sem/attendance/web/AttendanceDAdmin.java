package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.faces.utils.SelectableList;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.attendance.dto.AttendanceDto;
import com.bee32.sem.attendance.entity.AttendanceType;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.util.ColumnModel;
import com.bee32.sem.salary.util.MonthAttendanceModel;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    Date targetDate = new Date();
    int targetYear;
    int targetMonth;
    List<EmployeeInfoDto> allEmployees;
    List<ColumnModel> columns;

    String currentView = StandardViews.LIST;
    MonthAttendanceModel selectedMonthRecord;

    public AttendanceDAdmin() {
        generateColumns();
// AttendanceCriteria.getMonthList(new Date());
// for (EmployeeInfoDto employee : allEmployees) {
// AttendanceDto attendance = new AttendanceDto().create();
// attendance.setEmployee(employee);
// attendance.setDate(targetDate);
// attendances.add(attendance);
// }
    }

    List<MonthAttendanceModel> generatorAttendanceMRecord() {

        allEmployees = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);
        List<MonthAttendanceModel> month_record = new ArrayList<MonthAttendanceModel>();

        int days = SalaryDateUtil.getDayNumberOfMonth(targetDate);

        for (EmployeeInfoDto employee : allEmployees) {
            List<AttendanceDto> mlist = new ArrayList<AttendanceDto>();
            MonthAttendanceModel model = new MonthAttendanceModel();
            model.setEmployee(employee);
            for (int i = 1; i <= days; i++) {
                AttendanceDto day_attendance = new AttendanceDto();
                day_attendance.setDate(SalaryDateUtil.convertToDate(targetYear, targetMonth, i));
                day_attendance.setEmployee(employee);
                day_attendance.setMorning(AttendanceType.normal);
                day_attendance.setAfternoon(AttendanceType.normal);
                day_attendance.setEvening(AttendanceType.notAvailable);
                mlist.add(day_attendance);
            }
            model.setAttendances(mlist);
            month_record.add(model);
        }

        return month_record;
    }

    void generateColumns() {
        if (columns == null)
            columns = new ArrayList<ColumnModel>();
        else
            columns.clear();

        int columnNumber = SalaryDateUtil.getDayNumberOfMonth(targetDate);
        for (int i = 1; i <= columnNumber; i++) {
            columns.add(new ColumnModel(Integer.toString(i), i - 1, 0));
        }
    }

    protected void showView(String viewName) {
        currentView = viewName;
    }

    @Operation
    public void showContent() {
        if (getSelection().isEmpty()) {
            uiLogger.error("没有选定对象!");
            return;
        }
        openSelection();
        showView(StandardViews.CONTENT);
    }

    @Operation
    public void showContent(Object selection) {
        setSingleSelection(selection);
        showContent();
    }

    public SelectableList<MonthAttendanceModel> getTest() {
        return SelectableList.decorate(generatorAttendanceMRecord());
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public MonthAttendanceModel getSelectedMonthRecord() {
        return selectedMonthRecord;
    }

    public void setSelectedMonthRecord(MonthAttendanceModel selectedMonthRecord) {
        this.selectedMonthRecord = selectedMonthRecord;
    }

}
