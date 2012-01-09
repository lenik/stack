package com.bee32.sem.purchase.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.bom.entity.Part;

/**
 * 生产任务明细
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_task_item_seq", allocationSize = 1)
public class MakeTaskItem
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_LENGTH = 40;

    MakeTask task;
    int index;
    Part part;
    BigDecimal quantity = new BigDecimal(1);

    Date deadline;
    String status;

    @NaturalId
    @ManyToOne(optional = false)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    /**
     * 单据内部的序号
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @Column(precision = QTY_ITEM_PRECISION, scale = QTY_ITEM_SCALE)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        if (quantity == null)
            throw new NullPointerException("quantity");
        this.quantity = quantity;
    }

    @Column(length = STATUS_LENGTH)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(task), //
                naturalId(part));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (task == null)
            throw new NullPointerException("task");
        if (part == null)
            throw new NullPointerException("part");
        return selectors(//
                selector(prefix + "task", task), //
                selector(prefix + "part", part));
    }

    @Override
    public MakeTaskItem detach() {
        super.detach();
        task = null;
        return this;
    }
}
