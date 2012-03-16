package com.bee32.sem.asset.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.plover.orm.cache.Redundant;
import com.bee32.plover.orm.entity.CopyUtils;
import com.bee32.plover.ox1.config.DecimalConfig;
import com.bee32.sem.process.base.ProcessEntity;
import com.bee32.sem.process.verify.AbstractVerifyProcessHandler;
import com.bee32.sem.process.verify.IVerifyProcessAware;
import com.bee32.sem.process.verify.IVerifyProcessHandler;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;
import com.bee32.sem.world.monetary.FxrQueryException;
import com.bee32.sem.world.monetary.MCValue;
import com.bee32.sem.world.monetary.MCVector;

/**
 * 会计凭证
 *
 * @author jack
 * @author lenik
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_ticket_seq", allocationSize = 1)
public class AccountTicket
        extends ProcessEntity
        implements IVerifyProcessAware, DecimalConfig {

    private static final long serialVersionUID = 1L;

    List<AccountTicketItem> items = new ArrayList<AccountTicketItem>();
    transient MCVector total; // Redundant
    BigDecimal nativeTotal; // Redundant.

    BudgetRequest request;
    SingleVerifierWithNumberSupport verifyContext;

    public AccountTicket() {
        setDate(new Date());
        setVerifyContext(new SingleVerifierWithNumberSupport());
    }

    @Override
    public void populate(Object source) {
        if (source instanceof AccountTicket)
            _populate((AccountTicket) source);
        else
            super.populate(source);
    }

    protected void _populate(AccountTicket o) {
        super._populate(o);
        items = CopyUtils.copyList(o.items);
        total = o.total;
        nativeTotal = o.nativeTotal;
        request = o.request;
        verifyContext = (SingleVerifierWithNumberSupport) o.verifyContext.clone();
    }

    @Transient
    public Date getDate() {
        return getBeginTime();
    }

    public void setDate(Date date) {
        setBeginTime(date);
        setEndTime(date);
    }

    /**
     * 会计凭证上的条目列表
     */
    @OneToMany(mappedBy = "ticket", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public List<AccountTicketItem> getItems() {
        return items;
    }

    public void setItems(List<AccountTicketItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
        invalidateTotal();
    }

    public synchronized void addItem(AccountTicketItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
        invalidateTotal();
    }

    public synchronized void removeItem(AccountTicketItem item) {
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

    @OneToOne
    public BudgetRequest getRequest() {
        return request;
    }

    public void setRequest(BudgetRequest request) {
        this.request = request;
    }

    /**
     * 多币种表示的金额。
     */
    @Transient
    public synchronized MCVector getTotal() {
        if (total == null) {
            total = new MCVector();
            for (AccountTicketItem item : items) {
                MCValue itemTotal = item.getValue();
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
                    for (AccountTicketItem item : items) {
                        BigDecimal itemNativeTotal = item.getNativeValue();
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

    @Transient
    @Override
    public String getNumberDescription() {
        return "金额";
    }

    @Override
    protected Number computeJudgeNumber()
            throws Exception {
        return getNativeTotal();
    }

    @Transient
    @Override
    public IVerifyProcessHandler getVerifyProcessHandler() {
        return new AbstractVerifyProcessHandler() {
            @Override
            public void preUpdate() {
                // distribute the verify-context to all items'.
                for (AccountTicketItem item : items)
                    item.getVerifyContext().populate(getVerifyContext());
            }
        };
    }

}
