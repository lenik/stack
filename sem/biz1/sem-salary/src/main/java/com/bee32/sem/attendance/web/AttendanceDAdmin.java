package com.bee32.sem.attendance.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bee32.plover.arch.operation.Operation;
import com.bee32.plover.orm.util.EntityViewBean;
import com.bee32.plover.restful.resource.StandardViews;
import com.bee32.sem.hr.dto.EmployeeInfoDto;
import com.bee32.sem.hr.entity.EmployeeInfo;
import com.bee32.sem.salary.util.ColumnModel;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class AttendanceDAdmin
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    Date targetDate = new Date();
    int targetYear;
    int targetMonth;
    List<EmployeeInfoDto> allEmps;
    List<ColumnModel> columns;

    String currentView = StandardViews.LIST;

    public AttendanceDAdmin() {
        generateColumns();
    }

    void generateColumns() {
        if (columns == null)
            columns = new ArrayList<ColumnModel>();
        else
            columns.clear();

        int columnNumber = SalaryDateUtil.getDayNumberOfMonth(targetDate);
        for (int i = 0; i < columnNumber; i++) {
            columns.add(new ColumnModel(Integer.toString(i + 1), i, 0));
        }
    }

    void generateAttendance() {
        allEmps = mrefList(EmployeeInfo.class, EmployeeInfoDto.class, 0);

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

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

}
