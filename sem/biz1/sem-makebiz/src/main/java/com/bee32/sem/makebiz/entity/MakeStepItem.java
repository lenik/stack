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

import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.make.entity.QCResult;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Person;

/**
 * 工艺点名细
 *
 * beginTime作为发生日期
 * 同时，作为StockJob的子类，可以持人各类仓库单据StockOrder,因为生产数据中包含了成品，半成品，原材料的出入库
 *
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_step_item_seq", allocationSize = 1)
public class MakeStepItem
    extends StockJob
    implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    MakeStep parent;

    BigDecimal planQuantity = new BigDecimal(0);
    BigDecimal actualQuantity = new BigDecimal(0);
    BigDecimal verifiedQuantity = new BigDecimal(0);

    Date planDeadline;
    Date actualDeadline;

    OrgUnit orgUnit;
    List<Person> operators = new ArrayList<Person>();
    QCResult qcResult = new QCResult();


    @Override
    public void populate(Object source) {
        if (source instanceof MakeStepItem)
            _populate((MakeStepItem) source);
        else
            super.populate(source);
    }

    protected void _populate(MakeStepItem o) {
        super._populate(o);
        parent = o.parent;
        planQuantity = o.planQuantity;
        actualQuantity = o.actualQuantity;
        verifiedQuantity = o.verifiedQuantity;

        planDeadline = o.planDeadline;
        actualDeadline = o.actualDeadline;
        orgUnit = o.orgUnit;
        operators = CopyUtils.copyList(o.operators);
        qcResult = o.qcResult;
    }

    /**
     * 工艺点
     *
     *
     */
    @ManyToOne(optional = false)
    public MakeStep getParent() {
        return parent;
    }

    public void setParent(MakeStep parent) {
        this.parent = parent;
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
    @JoinTable(name = "MakeStepItemOperator",
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
        if (qcResult == null)
            throw new NullPointerException("qcResult");
        this.qcResult = qcResult;
    }

}
