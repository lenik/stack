package com.bee32.sem.makebiz.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_process_seq", allocationSize = 1)
public class MakeProcess
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int BATCH_NUMBER_LENGTH = 50;

    MakeTask task;
    Part part;
    BigDecimal quantity = new BigDecimal(1);
    String batchNumber;
    Date deadline;

    List<MakeStep> steps = new ArrayList<MakeStep>();
    List<SerialNumber> serials = new ArrayList<SerialNumber>();

    @ManyToOne(optional = false)
    public MakeTask getTask() {
        return task;
    }

    public void setTask(MakeTask task) {
        this.task = task;
    }

    @ManyToOne(optional = false)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

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

    @Column(length = BATCH_NUMBER_LENGTH)
    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MakeStep> getSteps() {
        return steps;
    }

    public void setSteps(List<MakeStep> steps) {
        this.steps = steps;
    }

    @OneToMany(mappedBy = "process")
    public List<SerialNumber> getSerials() {
        return serials;
    }

    public void setSerials(List<SerialNumber> serials) {
        this.serials = serials;
    }

}
