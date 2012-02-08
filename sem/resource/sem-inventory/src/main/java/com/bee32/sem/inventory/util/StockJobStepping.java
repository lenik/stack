package com.bee32.sem.inventory.util;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.SqlRestriction;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.entity.StockJob;

public class StockJobStepping
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Class<? extends StockJob> jobClass;
    Class<? extends StockJobDto<?>> jobDtoClass;

    String initiatorProperty;
    String initiatorColumn;

    String bindingProperty;
    String bindingColumn;

    public StockJobStepping() {
    }

    public Class<? extends StockJob> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends StockJob> jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobTableName() {
        PloverNamingStrategy namingStrategy = PloverNamingStrategy.getDefaultInstance();
        String tableName = namingStrategy.classToTableName(jobClass.getCanonicalName());
        return tableName;
    }

    public Class<? extends StockJobDto<?>> getJobDtoClass() {
        return jobDtoClass;
    }

    public void setJobDtoClass(Class<? extends StockJobDto<?>> jobDtoClass) {
        this.jobDtoClass = jobDtoClass;
    }

    public String getInitiatorProperty() {
        return initiatorProperty;
    }

    public void setInitiatorProperty(String initiatorProperty) {
        this.initiatorProperty = initiatorProperty;
    }

    public String getInitiatorColumn() {
        return initiatorColumn;
    }

    public void setInitiatorColumn(String initiatorColumn) {
        this.initiatorColumn = initiatorColumn;
    }

    public String getBindingProperty() {
        return bindingProperty;
    }

    public void setBindingProperty(String bindingProperty) {
        this.bindingProperty = bindingProperty;
    }

    public String getBindingColumn() {
        return bindingColumn;
    }

    public void setBindingColumn(String bindingColumn) {
        this.bindingColumn = bindingColumn;
    }

    /**
     * 在当前阶段等待处理的库存作业
     */
    public CriteriaElement getJobQueueing() {
        return new IsNull(bindingProperty);
    }

    /**
     * 在当前阶段等待处理的库存单据（即，danglingOrder）
     */
    public CriteriaElement getOrderQueueing() {
        if (initiatorColumn.equals(bindingColumn))
            throw new IllegalUsageException("排队单据在作业发起阶段时不可用。");
        String jobTable = getJobTableName();
        String sql = "id in (select " + initiatorColumn + " from " + jobTable + " where " + bindingColumn + " is null)";
        return new SqlRestriction(sql);
    }

}
