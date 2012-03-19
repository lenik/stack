package com.bee32.sem.makebiz.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NaturalId;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.make.entity.MakeStep;
import com.bee32.sem.make.entity.Part;
import com.bee32.sem.people.entity.OrgUnit;

/**
 * 工艺流转单明细
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_step_instance_seq", allocationSize = 1)
public class MakeStepInstance
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeProcess process;

    MakeStep step;
    Part part;

    BigDecimal palnQuantity = new BigDecimal(1);
    BigDecimal actualQuantity = new BigDecimal(1);

    Date planDeadline;
    Date actualDeadline;

    OrgUnit orgUnit;
    List<MakeStepOperator> operators;
    List<QualityCheck> qcs;

    boolean flag;

    @ManyToOne(optional=false)
    public MakeProcess getProcess() {
        return process;
    }

    public void setProcess(MakeProcess process) {
        this.process = process;
    }

    @NaturalId
    @ManyToOne(optional=false)
    public MakeStep getStep() {
        return step;
    }

    public void setStep(MakeStep step) {
        this.step = step;
    }

    @NaturalId
    @ManyToOne(optional=false)
    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getPalnQuantity() {
        return palnQuantity;
    }

    public void setPalnQuantity(BigDecimal palnQuantity) {
        this.palnQuantity = palnQuantity;
    }

    public void setPalnQuantity(long palnQuantity) {
        setPalnQuantity(new BigDecimal(palnQuantity));
    }

    public void setPalnQuantity(double palnQuantity) {
        setPalnQuantity(new BigDecimal(palnQuantity));
    }

    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(BigDecimal actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public void setActualQuantity(long actualQuantity) {
        setActualQuantity(new BigDecimal(actualQuantity));
    }

    public void setActualQuantity(double actualQuantity) {
        setActualQuantity(new BigDecimal(actualQuantity));
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getPlanDeadline() {
        return planDeadline;
    }

    public void setPlanDeadline(Date planDeadline) {
        this.planDeadline = planDeadline;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getActualDeadline() {
        return actualDeadline;
    }

    public void setActualDeadline(Date actualDeadline) {
        this.actualDeadline = actualDeadline;
    }

    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    @OneToMany(mappedBy="instance")
    public List<MakeStepOperator> getOperators() {
        return operators;
    }

    public void setOperators(List<MakeStepOperator> operators) {
        this.operators = operators;
    }

    @OneToMany(mappedBy="instance")
    public List<QualityCheck> getQcs() {
        return qcs;
    }

    public void setQcs(List<QualityCheck> qcs) {
        this.qcs = qcs;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
