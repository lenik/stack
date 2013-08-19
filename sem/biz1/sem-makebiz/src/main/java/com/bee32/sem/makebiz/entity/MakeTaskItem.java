package com.bee32.sem.makebiz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.arch.util.IdComposite;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.ox1.c.CEntity;
import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.material.entity.Material;

/**
 * 生产任务明细
 *
 * 生产任务明细项目。
 *
 * <p lang="en">
 * Make Task Item
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
    Material material;
    BigDecimal quantity = new BigDecimal(0);

    Date deadline;
    String status;

    List<MakeProcess> processes;

    @Override
    public void populate(Object source) {
        if (source instanceof MakeTaskItem)
            _populate((MakeTaskItem) source);
        else
            super.populate(source);
    }

    protected void _populate(MakeTaskItem o) {
        super._populate(o);
        task = o.task;
        index = o.index;
        material = o.material;
        quantity = o.quantity;
        deadline = o.deadline;
        status = o.status;
    }

    /**
     * 生产任务
     *
     * 生产任务主控类。
     *
     * @return
     */
    @NaturalId
    @ManyToOne(optional = false)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    /**
     * 序号
     *
     * 单据内部的序号，在办面上显示的进修发挥作用。
     */
    @Column(nullable = false)
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * 物料
     *
     * 生产任务对应的需要生产的物料。
     *
     * @return
     */
    @NaturalId
    @ManyToOne
    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * 生产数量
     *
     * 本生产任务需要生产的产品数量。
     *
     * @return
     */
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

    /**
     * 交货期限
     *
     * 生产任务上的交货期限。比订单上的交货期限一般要提前一些。
     *
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * 工艺流转单
     *
     * 本条生产任务对应的工艺流转单列表。
     *
     * @return
     */
    @OneToMany(mappedBy = "taskItem", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @OrderBy("id ASC")
    public List<MakeProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(List<MakeProcess> processes) {
        this.processes = processes;
    }

    @Override
    protected Serializable naturalId() {
        return new IdComposite(//
                naturalId(task), //
                naturalId(material));
    }

    @Override
    protected ICriteriaElement selector(String prefix) {
        if (task == null)
            throw new NullPointerException("task");
        if (material == null)
            throw new NullPointerException("material");
        return selectors(//
                selector(prefix + "task", task), //
                selector(prefix + "part", material));
    }

    @Override
    public MakeTaskItem detach() {
        super.detach();
        task = null;
        return this;
    }

    @Override
    protected CEntity<?> owningEntity() {
        return getTask();
    }
}
