package com.bee32.sem.inventory.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Index;

import com.bee32.plover.arch.bean.IPropertyAccessor;
import com.bee32.plover.arch.generic.IParameterized;
import com.bee32.plover.arch.generic.IParameterizedType;
import com.bee32.plover.arch.util.IEnclosedObject;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.inventory.process.IStockOrderVerifyContext;
import com.bee32.sem.inventory.process.StockOrderVerifySupport;
import com.bee32.sem.inventory.tx.entity.StockJob;
import com.bee32.sem.inventory.tx.entity.StockOutsourcing;
import com.bee32.sem.inventory.tx.entity.StockTransfer;
import com.bee32.sem.inventory.util.BatchArray;
import com.bee32.sem.material.entity.Material;
import com.bee32.sem.material.entity.MaterialPreferredLocation;
import com.bee32.sem.material.entity.StockWarehouse;
import com.bee32.sem.people.entity.OrgUnit;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.thing.AbstractItemList;

/**
 * 库存通用定单
 */
@Entity(name = "StockOrder")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@DiscriminatorValue("--")
@SequenceGenerator(name = "idgen", sequenceName = "stock_order_seq", allocationSize = 1)
public class AbstractStockOrder<Item extends StockOrderItem>
        extends AbstractStockItemList<Item>
        implements
            IParameterized,
            IVerifiable<IStockOrderVerifyContext>,
            IEnclosedObject<StockJob>,
            IAccountTicketSource {

    private static final long serialVersionUID = 1L;

    StockPeriod base;
    StockPeriod spec;
    StockOrderSubject subject;
    StockJob job;

    Chance chance;
    Party org;
    OrgUnit orgUnit;
    StockWarehouse warehouse; // Redundant.

    AccountTicket ticket;

    public AbstractStockOrder() {
        this(null, StockOrderSubject.INIT, null);
    }

    public AbstractStockOrder(StockPeriod base, StockOrderSubject subject) {
        this(base, subject, null);
    }

    public AbstractStockOrder(StockPeriod base, StockOrderSubject subject, StockWarehouse warehouse) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.base = base;
        this.subject = subject;
        if (subject.isPacking())
            this.spec = base;
        this.warehouse = warehouse;
    }

    @Override
    protected void createTransients() {
        if (verifyContext == null)
            verifyContext = new StockOrderVerifySupport();
        verifyContext.bind(this);
    }

    @Transient
    @Override
    public IParameterizedType getParameterizedType() {
        return StockOrderParameterizedType.INSTANCE;
    }

    @Transient
    @Override
    public StockJob getEnclosingObject() {
        return getJob();
    }

    @Override
    public void setEnclosingObject(StockJob enclosingObject) {
        setJob(enclosingObject);
    }

    @Override
    public void populate(Object source) {
        if (source instanceof AbstractStockOrder<?>)
            _populate((AbstractStockOrder<?>) source);
        else
            super.populate(source);
    }

    protected void _populate(AbstractStockOrder<?> o) {
        super._populate(o);
        base = o.base;
        subject = o.subject;
        spec = o.spec;
        job = o.job;
        chance = o.chance;
        org = o.org;
        orgUnit = o.orgUnit;
        warehouse = o.warehouse;
        ticket = o.ticket;
    }

    /**
     * 基准期结
     *
     * 基准的库存期结。
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
     * 参考期结
     *
     * 本单仅作为某期结的余单。（冗余）
     *
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
     * 科目
     *
     * 定义本单的用途。
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

    /**
     * 科目
     *
     * 定义本单的用途。
     */
    @Column(name = "subject", length = 4, nullable = false)
    @Index(name = "##_subject")
    String get_subject() {
        return subject.getValue();
    }

    void set_subject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = StockOrderSubject.forValue(subject);
    }

    /**
     * 参考库存作业
     *
     * 库存作业有多种类型，各自 id 独立，因此这里的参考库存作业具体对应为哪中作业类型要根据科目而定。（可选）
     *
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
    @Index(name = "##_job")
    @ManyToOne(fetch = FetchType.LAZY)
    public StockJob getJob() {
        return job;
    }

    /**
     * 参考库存作业
     *
     * 库存作业有多种类型，各自 id 独立，因此这里的参考库存作业具体对应为哪中作业类型要根据科目而定。（可选）
     *
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
    public void setJob(StockJob job) {
        this.job = job;
    }

    /**
     * 销售机会
     *
     * 仓库单据对应的销售机会
     */
    @ManyToOne
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 相关组织
     *
     * 出库单对应的客户或入库单对应的供应商。
     */
    @ManyToOne
    public Party getOrg() {
        return org;
    }

    public void setOrg(Party org) {
        this.org = org;
    }

    /**
     * 相关部门
     *
     * 相关组织下的具体部门。
     */
    @ManyToOne
    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    /**
     * 主仓库。
     *
     * 出入库的仓库。通常所有定单项目应该和本仓库一致。
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

    /**
     * 凭证
     *
     * 仓库单据对应的财务凭证。
     *
     * @return
     */
    @Override
    @ManyToOne
    public AccountTicket getTicket() {
        return ticket;
    }

    @Override
    public void setTicket(AccountTicket ticket) {
        this.ticket = ticket;
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
    public <E extends AbstractStockOrder<Item>> E createPeerOrder(Class<E> stockOrderType,
            StockOrderSubject peerSubject, boolean copyItems) {
        if (job == null)
            throw new IllegalStateException("没有指定上层的库存作业，创建对等单据没有意义。");
        E peer;
        try {
            peer = stockOrderType.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        peer.base = base;
        peer.spec = spec;
        peer.job = job;
        peer.subject = peerSubject;
        if (copyItems) {
            peer.items.addAll(items);
            // 初始化对等单的项目状态为挂起。
            for (StockOrderItem peerItem : peer.items)
                peerItem.setState(StockItemState.PENDING);
        }
        return peer;
    }

    @Override
    public void addItem(Item item) {
        super.addItem(item);
        item.setParent(this);
    }

    public void addItem(Material material, String batch1, double quantity, Double price) {
        @SuppressWarnings("unchecked")
        Item item = (Item) new StockOrderItem(); // XXX

        item.setMaterial(material);
        item.setBatchArray(new BatchArray(batch1));
        item.setQuantity(quantity);
        if (price != null)
            item.setPrice(price);

        List<MaterialPreferredLocation> preferredLocations = material.getPreferredLocations();
        if (!preferredLocations.isEmpty())
            item.setLocation(preferredLocations.get(0).getLocation());

        addItem(item);
    }

    public static final IPropertyAccessor<StockOrderSubject> subjectProperty = _property_(//
            AbstractStockOrder.class, "subject");
    public static final IPropertyAccessor<BigDecimal> nativeTotalProperty = _property_(//
            AbstractItemList.class, "nativeTotal");

    StockOrderVerifySupport verifyContext;

    /**
     * 审核上下文
     */
    @Embedded
    @Override
    public StockOrderVerifySupport getVerifyContext() {
        return verifyContext;
    }

    public void setVerifyContext(StockOrderVerifySupport verifyContext) {
        this.verifyContext = verifyContext;
        verifyContext.bind(this);
    }


    /**
     * 凭证源Id
     */
    @Transient
    @Override
    public Serializable getTicketSrcId() {
        return this.getId();
    }

    /**
     * 凭证源类型
     */
    @Transient
    @Override
    public String getTicketSrcType() {
        return this.getSubject().getDisplayName();
    }

    /**
     * 凭证源摘要
     */
    @Transient
    @Override
    public String getTicketSrcLabel() {
        return this.getLabel();
    }

    /**
     * 凭证源金额
     */
    @Transient
    @Override
    public BigDecimal getTicketSrcValue() throws FxrQueryException {
        return this.getNativeTotal().abs();
    }
}
