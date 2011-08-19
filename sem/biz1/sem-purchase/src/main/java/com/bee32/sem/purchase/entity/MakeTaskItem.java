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
import com.bee32.plover.criteria.hibernate.And;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.orm.ext.color.MomentInterval;
import com.bee32.plover.orm.ext.config.DecimalConfig;
import com.bee32.sems.bom.entity.Part;

/**
 * 生产任务明细
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_task_item_seq", allocationSize = 1)
public class MakeTaskItem
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeTask task;
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

    @Column(length = 40)
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
        return new And(//
                selector(prefix + "task", task), //
                selector(prefix + "part", part));
    }

}
