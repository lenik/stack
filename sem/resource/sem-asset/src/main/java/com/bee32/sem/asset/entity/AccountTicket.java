package com.bee32.sem.asset.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.builtin.IJudgeNumber;
import com.bee32.sem.process.verify.builtin.ISingleVerifierWithNumber;
import com.bee32.sem.process.verify.builtin.SingleVerifierWithNumberSupport;
import com.bee32.sem.world.monetary.FxrQueryException;

/**
 * 会计凭证
 *
 * @author jack
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_ticket_seq", allocationSize = 1)
public class AccountTicket
        extends TxEntity
        implements IVerifiable<ISingleVerifierWithNumber>, IJudgeNumber {

    private static final long serialVersionUID = 1L;

    SingleVerifierWithNumberSupport singleVerifierWithNumberSupport = new SingleVerifierWithNumberSupport(this);

    List<AccountTicketItem> items = new ArrayList<AccountTicketItem>();
    BudgetRequest request;

    public AccountTicket() {
        setDate(new Date());
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
    }

    public synchronized void addItem(AccountTicketItem item) {
        if (item == null)
            throw new NullPointerException("item");

        if (item.getIndex() == -1)
            item.setIndex(items.size());

        items.add(item);
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
     * 判断借贷是否相等
     * @throws FxrQueryException
     */
    @Transient
    public boolean isDebitCreditEqual() throws FxrQueryException {
        BigDecimal debitTotal = new BigDecimal(0);
        BigDecimal creditTotal = new BigDecimal(0);
        for(AccountTicketItem i : items) {
            if(i.isDebitSide()) {
                debitTotal = debitTotal.add(i.getValue().getNativeValue(getCreatedDate()).abs());
            } else {
                creditTotal = creditTotal.add(i.getValue().getNativeValue(getCreatedDate()).abs());
            }
        }

        if(debitTotal.compareTo(creditTotal) != 0) {
            return false;
        }

        return true;
    }

    public void setVerifyContext(SingleVerifierWithNumberSupport singleVerifierWithNumberSupport) {
        this.singleVerifierWithNumberSupport = singleVerifierWithNumberSupport;
        singleVerifierWithNumberSupport.bind(this);
    }

    @Embedded
    @Override
    public SingleVerifierWithNumberSupport getVerifyContext() {
        return singleVerifierWithNumberSupport;
    }

    @Transient
    @Override
    public String getNumberDescription() {
        return "金额";
    }

    @Transient
    @Override
    public Number getJudgeNumber() {
        try {
            BigDecimal total = new BigDecimal(0);
            for(AccountTicketItem item:items) {
                total = total.add(item.value.getNativeValue(item.getDate()));
            }

            return total;
        } catch (FxrQueryException e) {
            throw new RuntimeException(e);
        }
    }
}
