package com.bee32.sem.inventory.util;

import java.io.Serializable;

import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.entity.StockJob;

public class StockJobStepping
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Class<? extends StockJob> jobClass;
    Class<? extends StockJobDto<?>> jobDtoClass;

    boolean primary;

    String bindingPropertyName;
    String bindingColumnName;

    public StockJobStepping() {
    }

    public Class<? extends StockJob> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends StockJob> jobClass) {
        this.jobClass = jobClass;
    }

    public Class<? extends StockJobDto<?>> getJobDtoClass() {
        return jobDtoClass;
    }

    public void setJobDtoClass(Class<? extends StockJobDto<?>> jobDtoClass) {
        this.jobDtoClass = jobDtoClass;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getBindingPropertyName() {
        return bindingPropertyName;
    }

    public void setBindingPropertyName(String bindingPropertyName) {
        this.bindingPropertyName = bindingPropertyName;
    }

    public String getBindingColumnName() {
        return bindingColumnName;
    }

    public void setBindingColumnName(String bindingColumnName) {
        this.bindingColumnName = bindingColumnName;
    }
//
//    public <D extends StockJobDto<?>> D createJobDto() {
//        jobDtoClass.newInstance();
//    }

}
