package com.bee32.sem.purchase.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.chance.entity.Chance;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

/**
 * 生产定单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "make_order_seq", allocationSize = 1)
public class MakeOrder
        extends TxEntity
        implements DecimalConfig {

    private static final long serialVersionUID = 1L;

    public static final int STATUS_LENGTH = 30;

    Party customer;
    Date deadline;
    String status;
    Chance chance;

    List<MakeOrderItem> items = new ArrayList<MakeOrderItem>();

    List<MakeTask> tasks = new ArrayList<MakeTask>();

    MCVector total;
    BigDecimal nativeTotal; // Redundant.

    @ManyToOne(optional = false)
    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        if (customer == null)
            throw new NullPointerException("customer");
        this.customer = customer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Column(length = STATUS_LENGTH)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<MakeOrderItem> getItems() {
        return items;
    }

    public void setItems(List<MakeOrderItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    public synchronized void addItem(MakeOrderItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
        invalidateTotal();
    }

    public synchronized void removeItem(MakeOrderItem item) {
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

        invalidateTotal();
    }

    public synchronized void reindex() {
        for (int index = items.size() - 1; index >= 0; index--)
            items.get(index).setIndex(index);
    }

    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<MakeTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MakeTask> tasks) {
        if (tasks == null)
            throw new NullPointerException("tasks");
        this.tasks = tasks;
    }

    @OneToOne
    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    /**
     * 多币种表示的金额。
     */
    @Transient
    public synchronized MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (MakeOrderItem item : items) {
                MCValue itemTotal = item.getTotal();
                total.add(itemTotal);
            }
        }
        return total;
    }

    /**
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
                    for (MakeOrderItem item : items) {
                        BigDecimal itemNativeTotal = item.getNativePrice();
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

}
