package com.bee32.sem.makebiz.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.NaturalId;

import com.bee32.plover.ox1.color.MomentInterval;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.make.entity.MakeStepModel;
import com.bee32.sem.make.entity.QCResult;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Person;

/**
 * 工艺流转单明细
 *
 * @author jack
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_step_instance_seq", allocationSize = 1)
public class MakeStep
        extends MomentInterval
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeProcess parent;

    // Behavior as <model.part, model.processOrder>.
    MakeStepModel model;

    BigDecimal planQuantity = new BigDecimal(1);
    BigDecimal actualQuantity = new BigDecimal(1);

    Date planDeadline;
    Date actualDeadline;

    OrgUnit orgUnit;
    List<Person> operators = new ArrayList<Person>();
    QCResult qcResult = new QCResult();

    boolean done;

    @ManyToOne(optional = false)
    public MakeProcess getParent() {
        return parent;
    }

    public void setParent(MakeProcess parent) {
        this.parent = parent;
    }

    @NaturalId
    @ManyToOne(optional = false)
    public MakeStepModel getModel() {
        return model;
    }

    public void setModel(MakeStepModel model) {
        this.model = model;
    }


    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getPlanQuantity() {
        return planQuantity;
    }

    public void setPlanQuantity(BigDecimal planQuantity) {
        this.planQuantity = planQuantity;
    }

    public void setPlanQuantity(long planQuantity) {
        setPlanQuantity(new BigDecimal(planQuantity));
    }

    public void setPlanQuantity(double planQuantity) {
        setPlanQuantity(new BigDecimal(planQuantity));
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

    @ManyToMany
    @JoinTable(name = "MakeStepOperator",
    /*            */joinColumns = @JoinColumn(name = "makeStep"), //
    /*            */inverseJoinColumns = @JoinColumn(name = "operator"))
    public List<Person> getOperators() {
        return operators;
    }

    public void setOperators(List<Person> operators) {
        if (operators == null)
            throw new NullPointerException("operators");
        this.operators = operators;
    }

    @ManyToOne(optional = false)
    @Cascade(CascadeType.ALL)
    public QCResult getQcResult() {
        return qcResult;
    }

    public void setQcResult(QCResult qcResult) {
        this.qcResult = qcResult;
    }

    @Column(nullable = false)
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
