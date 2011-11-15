package com.bee32.sem.asset.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 会计凭证
 *
 * @author jack
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "account_ticket_seq", allocationSize = 1)
public class AccountTicket
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    List<AccountTicketItem> items = new ArrayList<AccountTicketItem>();
    BudgetRequest request;

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

    public BudgetRequest getRequest() {
        return request;
    }

    public void setRequest(BudgetRequest request) {
        this.request = request;
    }

}
