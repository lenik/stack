package com.bee32.sem.makebiz.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.make.entity.Part;

/**
 * 工艺流转单
 *
 * 根据产品的BOM和预设的工艺参数，由生产任务形成工艺流转单。
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_process_seq", allocationSize = 1)
public class MakeProcess
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int BATCH_NUMBER_LENGTH = 50;

    MakeTaskItem taskItem;
    Part part;
    BigDecimal quantity = new BigDecimal(0);
    String batchNumber;
    Date deadline;

    List<MakeStep> steps = new ArrayList<MakeStep>();
    List<SerialNumber> serials = new ArrayList<SerialNumber>();

    /**
     * 生产任务明细
     *
     * 一个工艺流转单对应一个生产任务单上的明细项目。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public MakeTaskItem getTaskItem() {
	return taskItem;
    }

	public void setTaskItem(MakeTaskItem taskItem) {
	this.taskItem = taskItem;
    }

	public void setTaskItemEven(MakeTaskItem taskItem) {
		this.taskItem = taskItem;
		this.part = taskItem.getPart();
		this.quantity = taskItem.getQuantity();
		this.deadline = taskItem.deadline;
	}

	/**
	 * 产品
	 *
	 * 工艺流转单对应的产品。
	 *
	 * @return
	 */
	@ManyToOne(optional = false)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    /**
     * 数量
     *
     * 产口生产数量。
     *
     * @return
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setQuantity(long quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    public void setQuantity(double quantity) {
        setQuantity(new BigDecimal(quantity));
    }

    /**
     * 批号
     *
     * 产品的生产批号。
     *
     * @return
     */
    @Column(length = BATCH_NUMBER_LENGTH)
    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * 生产期限
     *
     * 产品需要此日期前生产完成。
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
     * 工艺步骤
     *
     * 工艺流转单对应的所有生产步骤列表。
     *
     * @return
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @OrderBy("id ASC")
    public List<MakeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<MakeStep> steps) {
        this.steps = steps;
    }

    public synchronized void addStep(MakeStep step) {
        if (step == null)
            throw new NullPointerException("step");

        step.setParent(this);
        steps.add(step);
    }

    public synchronized void removeStep(MakeStep step) {
        if (step == null)
            throw new NullPointerException("step");

        int index = steps.indexOf(step);
        if (index == -1)
            return /* false */;

        steps.remove(index);
        step.detach();
    }

    /**
     * 序列号
     *
     * 如果管理细致，则每个产品都可以赋予一个编号，即为序列号。
     *
     * @return
     */
    @OneToMany(mappedBy = "process")
    public List<SerialNumber> getSerials() {
        return serials;
    }

    public void setSerials(List<SerialNumber> serials) {
        this.serials = serials;
    }

}
