package com.bee32.sem.salary.salary;

import java.io.Serializable;

public class SalaryDefPreview
        implements Serializable {

    private static final long serialVersionUID = 1L;

    String employee;
    Number previewValue;

    public SalaryDefPreview(String employee, Number previewValue) {
        this.employee = employee;
        this.previewValue = previewValue;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Number getPreviewValue() {
        return previewValue;
    }

    public void setPreviewValue(Number previewValue) {
        this.previewValue = previewValue;
    }

}
