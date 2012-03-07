package com.bee32.sem.purchase.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 送货单
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "delivery_note_seq", allocationSize = 1)
public class DeliveryNote
        extends ProcessEntity {

    private static final long serialVersionUID = 1L;

    MakeOrder order;
    Party customer;
    Date arrivalDate;
    List<DeliveryNoteItem> items = new ArrayList<DeliveryNoteItem>();
    DeliveryNoteTakeOut takeOut;

    @ManyToOne(optional = false)
    public MakeOrder getOrder() {
        return order;
    }

    public void setOrder(MakeOrder order) {
        if (order == null)
            throw new NullPointerException("order");
        this.order = order;
    }

    @ManyToOne(optional = false)
    public Party getCustomer() {
        return customer;
    }

    public void setCustomer(Party customer) {
        this.customer = customer;
    }

    /**
     * 按指定时间送达货物
     * @return
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

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



    @OneToOne(orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    public DeliveryNoteTakeOut getTakeOut() {
        return takeOut;
    }

    public void setTakeOut(DeliveryNoteTakeOut takeOut) {
        this.takeOut = takeOut;
    }



}
