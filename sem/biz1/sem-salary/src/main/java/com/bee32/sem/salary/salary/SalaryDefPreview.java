package com.bee32.sem.salary.salary;

import java.io.Serializable;

import com.bee32.sem.hr.dto.EmployeeInfoDto;

public class SalaryDefPreview
        implements Serializable {

    private static final long serialVersionUID = 1L;
    EmployeeInfoDto employee;
    Object previewValue;

    public SalaryDefPreview(EmployeeInfoDto employee, Object previewValue) {
        this.employee = employee;
        this.previewValue = previewValue;
    }

    public EmployeeInfoDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeInfoDto employee) {
        this.employee = employee;
    }

    public Object getPreviewValue() {
        return previewValue;
    }

    public void setPreviewValue(Object previewValue) {
        this.previewValue = previewValue;
    }
}
