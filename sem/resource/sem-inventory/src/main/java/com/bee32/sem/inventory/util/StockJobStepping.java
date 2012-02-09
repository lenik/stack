package com.bee32.sem.inventory.util;

import java.io.Serializable;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.bean.BeanPropertyAccessor;
import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.criteria.hibernate.SqlRestriction;
import com.bee32.plover.orm.PloverNamingStrategy;
import com.bee32.plover.orm.util.DTOs;
import com.bee32.sem.inventory.dto.StockOrderDto;
import com.bee32.sem.inventory.entity.StockOrder;
import com.bee32.sem.inventory.tx.dto.StockJobDto;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.misc.SevbFriend;
import com.bee32.sem.misc.UnmarshalMap;

public class StockJobStepping
        extends SevbFriend
        implements Serializable {

    private static final long serialVersionUID = 1L;

    Class<? extends StockJob> jobClass;
    Class<? extends StockJobDto<StockJob>> jobDtoClass;

    String initiatorProperty;
    String initiatorColumn;

    String bindingProperty;
    String bindingColumn;

    public StockJobStepping() {
    }

    @Override
    protected void select(Object mainEntry) {
        StockOrderDto order = (StockOrderDto) mainEntry;
        setSingleSelection(order);
    }

    @Override
    protected void open(Object mainOpenedObject) {
        StockOrderDto initiatorOrder = (StockOrderDto) mainOpenedObject;
        StockJobDto<?> job = null;
        if (!initiatorOrder.isNewCreated()) {
            long orderId = initiatorOrder.getId();
            try {
                StockJob _job = asFor(jobClass).getFirst(new Equals(bindingProperty + ".id", orderId));
                if (_job != null)
                    job = DTOs.marshal(jobDtoClass, _job);
            } catch (Exception e) {
                uiLogger.error("无法获取作业对象", e);
            }
        }
        if (job == null && isInitiator()) // Re-Create the missing job for secondary steppings.
            try {
                job = jobDtoClass.newInstance();
                job.create();
                setJobDtoBinding(job, initiatorOrder);
            } catch (ReflectiveOperationException e) {
                uiLogger.error("无法创建作业对象", e);
                return;
            }
        setOpenedObject(job);
    }

    @Override
    public boolean preUpdate(UnmarshalMap uMap) {
        if (getOpenedObject() == null) {
            uiLogger.error("没有指定对应的作业。");
            return false;
        }
        return true;
    }

    @Override
    public void saveOpenedObject(int saveFlags, UnmarshalMap uMapMain) {
        StockJobDto<?> job = getOpenedObject();
        try {
            StockJob _job = job.unmarshal(this);
            for (StockOrder _order : uMapMain.<StockOrder> entitySet())
                setJobBinding(_job, _order);
            asFor(jobClass).saveOrUpdate(_job);
        } catch (Exception e) {
            uiLogger.error("无法保存作业对象", e);
        }
    }

    @Override
    public void deleteSelection(int deleteFlags) {
        StockOrderDto initiatorOrder = (StockOrderDto) getSingleSelection();
        if (initiatorOrder == null)
            return;
        try {
            if (isInitiator()) {
                asFor(jobClass).findAndDelete(new Equals(bindingProperty + ".id", initiatorOrder.getId()));
            } else {
                for (StockJob _job : asFor(jobClass).list(new Equals(bindingProperty + ".id", initiatorOrder.getId()))) {
                    setJobBinding(_job, null);
                    asFor(jobClass).update(_job);
                }
            }
        } catch (Exception e) {
            uiLogger.error("无法清理作业对象", e);
        }
    }

    /* ********************************************************************** */

    @LeftHand(StockJob.class)
    public CriteriaElement getRelatedJob(long stockOrderId) {
        return new Equals(bindingProperty + ".id", stockOrderId);
    }

    /**
     * 在当前阶段等待处理的库存作业
     */
    @LeftHand(StockJob.class)
    public CriteriaElement getJobQueueing() {
        return new IsNull(bindingProperty + ".id");
    }

    /**
     * 在当前阶段等待处理的库存单据（即，danglingOrder）
     */
    @LeftHand(StockOrder.class)
    public CriteriaElement getOrderQueueing() {
        if (isInitiator())
            throw new IllegalUsageException("排队单据在作业发起阶段时不可用。");
        String jobTable = getJobTableName();
        String sql = "id in (select " + initiatorColumn + " from " + jobTable + " where " + bindingColumn + " is null)";
        return new SqlRestriction(sql);
    }

    /* ********************************************************************** */

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

    @SuppressWarnings("unchecked")
    public void setJobDtoClass(Class<? extends StockJobDto<?>> jobDtoClass) {
        this.jobDtoClass = (Class<? extends StockJobDto<StockJob>>) jobDtoClass;
    }

    /**
     * 库存作业 StockJob 上，对应为发起单据的属性名称。
     */
    public String getInitiatorProperty() {
        return initiatorProperty;
    }

    public void setInitiatorProperty(String initiatorProperty) {
        this.initiatorProperty = initiatorProperty;
    }

    /**
     * 库存作业 StockJob 上，对应为发起单据的字段名称。
     */
    public String getInitiatorColumn() {
        return initiatorColumn;
    }

    public void setInitiatorColumn(String initiatorColumn) {
        this.initiatorColumn = initiatorColumn;
    }

    /**
     * 库存作业 StockJob 上，对应为当前步骤的单据的属性名称。
     */
    public String getBindingProperty() {
        return bindingProperty;
    }

    public void setBindingProperty(String bindingProperty) {
        this.bindingProperty = bindingProperty;
    }

    /**
     * 库存作业 StockJob 上，对应为当前步骤的单据的字段名称。
     */
    public String getBindingColumn() {
        return bindingColumn;
    }

    public void setBindingColumn(String bindingColumn) {
        this.bindingColumn = bindingColumn;
    }

    public boolean isInitiator() {
        return initiatorProperty.equals(bindingProperty);
    }

    protected void setJobBinding(StockJob job, Object value) {
        IPropertyAccessor<Object> jobBinding = BeanPropertyAccessor.access(jobClass, this.bindingProperty);
        jobBinding.set(job, value);
    }

    protected void setJobDtoBinding(StockJobDto<?> jobDto, Object value) {
        IPropertyAccessor<Object> jobDtoBinding = BeanPropertyAccessor.access(jobDtoClass, this.bindingProperty);
        jobDtoBinding.set(jobDto, value);
    }

}
