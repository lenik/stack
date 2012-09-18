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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

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
 * 产品的每一个生产步骤所对应的信息都记录在本类中。
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

    BigDecimal planQuantity = new BigDecimal(0);
    BigDecimal actualQuantity = new BigDecimal(0);
    BigDecimal verifiedQuantity = new BigDecimal(0);

    Date planDeadline;
    Date actualDeadline;

    OrgUnit orgUnit;
    List<Person> operators = new ArrayList<Person>();
    QCResult qcResult = new QCResult();

    boolean done;

    /**
     * 工艺流转单
     *
     * 工艺流转单主控类。
     *
     * @return
     */
    @NaturalId
    @ManyToOne(optional = false)
    public MakeProcess getParent() {
        return parent;
    }

    public void setParent(MakeProcess parent) {
        this.parent = parent;
    }

    /**
     * 工艺步骤
     *
     * 对应的标准工艺步骤。
     *
     * @return
     */
    @NaturalId
    @ManyToOne(optional = false)
    public MakeStepModel getModel() {
        return model;
    }

    public void setModel(MakeStepModel model) {
        this.model = model;
    }


    /**
     * 计划数量
     *
     * 当前生产步骤的产品计划生产数量。
     *
     * @return
     */
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

    /**
     * 实际数量
     *
     * 当前生产步骤的产品实际生产数量。
     *
     * @return
     */
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

    /**
     * 合格数量
     *
     * 经过质检后，后格的数量。
     *
     * @return
     */
    @Column(scale = QTY_ITEM_SCALE, precision = QTY_ITEM_PRECISION, nullable = false)
    public BigDecimal getVerifiedQuantity() {
        return verifiedQuantity;
    }

    public void setVerifiedQuantity(BigDecimal verifiedQuantity) {
        this.verifiedQuantity = verifiedQuantity;
    }

    public void setVerifiedQuantity(long verifiedQuantity) {
        setVerifiedQuantity(new BigDecimal(verifiedQuantity));
    }

    public void setVerifiedQuantity(double verifiedQuantity) {
        setVerifiedQuantity(new BigDecimal(verifiedQuantity));
    }

    /**
     * 不合格数量
     *
     * 实际数量减合格数量
     * @return
     */
    @Transient
    public BigDecimal getNotVerifiedQuantity() {
        return actualQuantity.subtract(verifiedQuantity);
    }

    /**
     * 计划完成时间
     *
     * 产品的计划完成时间。
     *
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPlanDeadline() {
        return planDeadline;
    }

    public void setPlanDeadline(Date planDeadline) {
        this.planDeadline = planDeadline;
    }

    /**
     * 实际完成时间
     *
     * 产品的实际完成时间。
     *
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getActualDeadline() {
        return actualDeadline;
    }

    public void setActualDeadline(Date actualDeadline) {
        this.actualDeadline = actualDeadline;
    }

    /**
     * 部门
     *
     * 本工艺步骤所对应的内部部门。
     *
     * @return
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 生产工人
     *
     * 在本工艺步骤中所生的产品具体的生产工人列表。
     *
     * @return
     */
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

    /**
     * 质检
     *
     * 本工艺步骤上的实际质检数据。
     *
     * @return
     */
    @OneToOne(orphanRemoval=true)
    @Cascade(CascadeType.SAVE_UPDATE )
    public QCResult getQcResult() {
        return qcResult;
    }

    public void setQcResult(QCResult qcResult) {
        this.qcResult = qcResult;
    }

    /**
     * 是否完成
     *
     * 本步骤是否完成。
     *
     * @return
     */
    @Column(nullable = false)
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
