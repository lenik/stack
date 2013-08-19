package com.bee32.sem.makebiz.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.DefaultValue;

import com.bee32.icsf.principal.User;
import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.asset.entity.AccountTicket;
import com.bee32.sem.asset.entity.IAccountTicketSource;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.process.state.util.StateInt;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

/**
 * 送货单
 *
 * 送货单主控类。
 *
 * <p lang="en">
 * Delivery Note
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "delivery_note_seq", allocationSize = 1)
public class DeliveryNote
        extends ProcessEntity
        implements IAccountTicketSource, DecimalConfig {

// private static final long serialVersionUID = 1L;
    public static final int APPROVE_MESSAGE_LENGTH = 200;

    MakeOrder order;
    Party customer;
    Date arrivalDate;
    List<DeliveryNoteItem> items = new ArrayList<DeliveryNoteItem>();
    DeliveryNoteTakeOut takeOut;

    AccountTicket ticket;

    transient MCVector total; // Redundant
    BigDecimal nativeTotal; // Redundant.

    @Override
    public void populate(Object source) {
        if (source instanceof DeliveryNote)
            _populate((DeliveryNote) source);
        else
            super.populate(source);
    }

    protected void _populate(DeliveryNote o) {
        super._populate(o);
        approveUser = o.approveUser;
        approved = o.approved;
        approveMessage = o.approveMessage;
    }

    /**
     * 订单
     *
     * 送货单对应的订单。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    /**
     * 客户
     *
     * 送货客户。
     *
     * @return
     */
    @ManyToOne(optional = false)
    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        this.customer = customer;
    }

    /**
     * 送达时间
     *
     * 按指定时间送达货物。
     */
    @Transient
    public Date getArrivalDate() {
        return getBeginTime();
    }

    public void setArrivalDate(Date arrivalDate) {
        setBeginTime(arrivalDate);
    }

    /**
     * 明细
     *
     * 送货明细列表。
     *
     * @return
     */
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<DeliveryNoteItem> getItems() {
        return items;
    }

    public void setItems(List<DeliveryNoteItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(DeliveryNoteItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
    }

    public synchronized void removeItem(MakeTaskItem item) {
        if (item == null)
            throw new NullPointerException("item");

        int index = items.indexOf(item);
        if (index == -1)
            return /* false */;

        items.remove(index);
        item.detach();

        // Renum [index, ..)
        for (int i = index; i < items.size(); i++)
            items.get(i).setIndex(i);
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    /**
     * 多币种金额
     *
     * 多币种表示的金额。
     */
    @Transient
    public synchronized MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (DeliveryNoteItem item : items) {
                MCValue itemTotal = item.getTotal();
                total.add(itemTotal);
            }
        }
        return total;
    }

    /**
     * 本币金额
     *
     * 【冗余】获取用本地货币表示的总金额。
     *
     * @throws FxrQueryException
     *             外汇查询异常。
     */
    @Redundant
    @Column(precision = MONEY_TOTAL_PRECISION, scale = MONEY_TOTAL_SCALE)
    public synchronized BigDecimal getNativeTotal()
            throws FxrQueryException {
        if (nativeTotal == null) {
            synchronized (this) {
                if (nativeTotal == null) {
                    BigDecimal sum = new BigDecimal(0L, MONEY_TOTAL_CONTEXT);
                    for (DeliveryNoteItem item : items) {
                        BigDecimal itemNativeTotal = item.getNativeTotal();
                        sum = sum.add(itemNativeTotal);
                    }
                    nativeTotal = sum;
                }
            }
        }
        return nativeTotal;
    }

    public void setNativeTotal(BigDecimal nativeTotal) {
        this.nativeTotal = nativeTotal;
    }

    @Override
    protected void invalidate() {
        super.invalidate();
        invalidateTotal();
    }

    protected void invalidateTotal() {
        total = null;
        nativeTotal = null;
    }

    /**
     * 出库单
     *
     * 在送货时，产品出库，这里即为对应的产品出库单。
     *
     * @return
     */
    @OneToOne(orphanRemoval = true, mappedBy = "deliveryNote")
    @Cascade(CascadeType.ALL)
    public DeliveryNoteTakeOut getTakeOut() {
        return takeOut;
    }

    public void setTakeOut(DeliveryNoteTakeOut takeOut) {
        this.takeOut = takeOut;
    }

    /**
     * 凭证
     *
     * 送货单对应的财务凭证。
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
        return "送货单";
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
    public BigDecimal getTicketSrcValue()
            throws FxrQueryException {
        return this.getNativeTotal();
    }

    @StateInt
    public static final int STATE_INIT = _STATE_0;

    @StateInt
    public static final int STATE_APPROVED = _STATE_NORMAL_END;

    @StateInt
    public static final int STATE_REJECTED = _STATE_ABNORMAL_END;

    User approveUser;
    boolean approved;
    String approveMessage = "";

    @ManyToOne
    public User getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(User approveUser) {
        this.approveUser = approveUser;
    }

    @Column(nullable = false)
    @DefaultValue("false")
    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Column(nullable = false, length = APPROVE_MESSAGE_LENGTH)
    @DefaultValue("''")
    public String getApproveMessage() {
        return approveMessage;
    }

    public void setApproveMessage(String approveMessage) {
        if (approveMessage == null)
            throw new NullPointerException("approveMessage");
        this.approveMessage = approveMessage;
    }

    @Override
    protected Integer stateCode() {
        if (approveUser == null)
            return STATE_INIT;

        if (approved)
            return STATE_APPROVED;
        else
            return STATE_REJECTED;
    }
}
