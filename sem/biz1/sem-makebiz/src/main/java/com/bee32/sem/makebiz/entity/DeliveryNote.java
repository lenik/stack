package com.bee32.sem.makebiz.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.people.entity.Party;
import com.bee32.sem.process.base.ProcessEntity;

/**
 * 送货单
 *
 * 送货单主控类。
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

    /**
     * 定单
     *
     * 送货单对应的定单。
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

}
