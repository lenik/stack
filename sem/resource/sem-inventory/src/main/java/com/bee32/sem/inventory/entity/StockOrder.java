package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.inventory.tx.entity.StockJob;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@DiscriminatorValue("-")
public class StockOrder
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    StockOrderSubject subject = StockOrderSubject.START;
    StockJob job;

    String serial;

    List<StockOrderItem> items = new ArrayList<StockOrderItem>();

    @Column(length = 3, nullable = false)
    String getSubject_() {
        return subject.getValue();
    }

    void setSubject_(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = StockOrderSubject.valueOf(subject);
    }

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
     * Serial ID, or Second ID.
     */
    @Column(length = 40)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    @OneToMany(mappedBy = "parent")
    @Cascade(CascadeType.ALL)
    public List<StockOrderItem> getItems() {
        return items;
    }

    public void setItems(List<StockOrderItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

}
