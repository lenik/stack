package com.bee32.sem.inventory.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;

import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.people.entity.Org;
import com.bee32.sem.people.entity.OrgUnit;

/**
 * 库存通用订单
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@DiscriminatorValue("-")
@SequenceGenerator(name = "idgen", sequenceName = "stock_order_seq", allocationSize = 1)
public class StockOrder
        extends StockItemList {

    private static final long serialVersionUID = 1L;

    StockPeriod base;
    StockPeriod spec;
    StockOrderSubject subject;
    Long jobId;

    Org org;
    OrgUnit orgUnit;

    StockWarehouse warehouse; // Redundant.

    public StockOrder() {
        this.subject = StockOrderSubject.INIT;
    }

    public StockOrder(StockPeriod base, StockOrderSubject subject) {
        this(base, subject, null);
    }

    public StockOrder(StockPeriod base, StockOrderSubject subject, StockWarehouse warehouse) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.base = base;
        this.subject = subject;
        if (subject.isPacking())
            this.spec = base;
        this.warehouse = warehouse;
    }

    @Override
    public void populate(Object source) {
        if (source instanceof StockOrder)
            _populate((StockOrder) source);
        else
            super.populate(source);
    }

    protected void _populate(StockOrder o) {
        super._populate(o);
        base = o.base;
        subject = o.subject;
        spec = o.spec;
        jobId = o.jobId;
        org = o.org;
        orgUnit = o.orgUnit;
        warehouse = o.warehouse;
    }

    /**
     * 基准库存期结。
     */
    @ManyToOne(optional = true)
    public StockPeriod getBase() {
        return base;
    }

    public void setBase(StockPeriod base) {
        // if (base == null)
        // throw new NullPointerException("base");
        this.base = base;
    }

    /**
     * 本单仅作为某期结的余单。（冗余）
     * <p>
     * （取值为 <code>null</code> 或与 {@link #getBase()} 相等）。
     *
     * @see StockOrderSubject#START
     */
    @OneToOne
    public StockPeriod getSpec() {
        return spec;
    }

    public void setSpec(StockPeriod spec) {
        this.spec = spec;
    }

    /**
     * 科目，定义本单的用途。
     */
    @Transient
    public StockOrderSubject getSubject() {
        return subject;
    }

    public void setSubject(StockOrderSubject subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    @Column(name = "subject", length = 4, nullable = false)
    @Index(name = "##_subject")
    String get_subject() {
        return subject.getValue();
    }

    void set_subject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = StockOrderSubject.valueOf(subject);
    }

    /**
     * 参考库存作业（可选）
     * <p>
     * 注意： 库存作业有多种类型，各自 id 独立，因此这里的参考库存作业具体对应为哪中作业类型要根据科目而定。
     * <p>
     * 比如：
     * <ul>
     * <li> {@link StockOrderSubject#XFER_IN 调拨入库} 和 {@link StockOrderSubject#XFER_OUT 调拨出库} 对应为
     * {@link StockTransfer 库存调拨作业}。
     * <li> {@link StockOrderSubject#OSP_OUT 委外出库} 和 {@link StockOrderSubject#OSP_IN 委外入库} 对应为
     * {@link StockOutsourcing 委外加工作业}。
     * </ul>
     *
     * @return 作业ID （根据用途对应具体的作业类型），如果没有对应作业则返回 <code>null</code>。
     */
    @Index(name = "##_jobId")
    public Long getJobId() {
        return jobId;
    }

    /**
     * 参考库存作业（可选）
     * <p>
     * 注意： 库存作业有多种类型，各自 id 独立，因此这里的参考库存作业具体对应为哪中作业类型要根据科目而定。
     * <p>
     * 比如：
     * <ul>
     * <li> {@link StockOrderSubject#XFER_IN 调拨入库} 和 {@link StockOrderSubject#XFER_OUT 调拨出库} 对应为
     * {@link StockTransfer 库存调拨作业}。
     * <li> {@link StockOrderSubject#OSP_OUT 委外出库} 和 {@link StockOrderSubject#OSP_IN 委外入库} 对应为
     * {@link StockOutsourcing 委外加工作业}。
     * </ul>
     *
     * @param jobId
     *            作业ID （根据用途对应具体的作业类型），如果没有对应作业则设置为 <code>null</code>。
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * 出库单对应的客户或入库单对应的供应商
     */
    @ManyToOne
    public Org getOrg() {
        return org;
    }

    public void setOrg(Org org) {
        this.org = org;
    }

    /**
     * 对应的部门
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 订单（首选）仓库。
     *
     * 通常所有订单项目应该和本仓库一致。
     */
    @ManyToOne(optional = false)
    public StockWarehouse getWarehouse() {
        return warehouse;
    }

    /**
     * @param warehouse
     *            should be non-<code>null</code>, however, maybe <code>null</code> in stock query.
     */
    public void setWarehouse(StockWarehouse warehouse) {
        if (warehouse == null)
            throw new NullPointerException("warehouse");
        this.warehouse = warehouse;
    }

    @Transient
    @Override
    public String getDisplayName() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(subject.getDisplayName());
        sb.append(']');
        if (label != null) {
            sb.append(' ');
            sb.append(label);
        }
        return sb.toString();
    }

    /**
     * 建立对等单
     *
     * @param peerSubject
     *            对等单据的科目。
     * @param copyItems
     *            是否复制所有明细项目。
     */
    public StockOrder createPeerOrder(StockOrderSubject peerSubject, boolean copyItems) {
        if (jobId == null)
            throw new IllegalStateException("没有指定上层的库存作业，创建对等单据没有意义。");
        StockOrder peer = new StockOrder();
        peer.base = base;
        peer.spec = spec;
        peer.jobId = jobId;
        peer.subject = peerSubject;
        if (copyItems) {
            peer.items.addAll(items);
            // 初始化对等单的项目状态为挂起。
            for (StockOrderItem peerItem : peer.items)
                peerItem.setState(StockItemState.PENDING);
        }
        return peer;
    }

}
