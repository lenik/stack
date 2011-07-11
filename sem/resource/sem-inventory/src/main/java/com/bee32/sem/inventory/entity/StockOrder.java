package com.bee32.sem.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.bee32.sem.base.tx.TxEntity;

/**
 * 库存通用订单
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "stereo", length = 3)
@DiscriminatorValue("-")
public class StockOrder
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    StockSnapshot base;
    StockSnapshot initTarget;
    StockOrderSubject subject = StockOrderSubject.START;
    String serial;
    List<StockOrderItem> items = new ArrayList<StockOrderItem>();
    Long jobRef;

    /**
     * 基准库存版本。
     */
    @ManyToOne(optional = false)
    public StockSnapshot getBase() {
        return base;
    }

    public void setBase(StockSnapshot base) {
        if (base == null)
            throw new NullPointerException("base");
        this.base = base;
    }

    /**
     * 初始化目标
     *
     * @see StockOrderSubject#START
     */
    @OneToOne
    public StockSnapshot getInitTarget() {
        return initTarget;
    }

    public void setInitTarget(StockSnapshot initTarget) {
        this.initTarget = initTarget;
    }

    /**
     * 科目，定义本订单的用途。
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

    @Column(length = 3, nullable = false)
    String get_Subject() {
        return subject.getValue();
    }

    void set_Subject(String subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = StockOrderSubject.valueOf(subject);
    }

    /**
     * 单据序列号。Serial ID, or Second ID.
     */
    @Column(length = 40)
    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * 单据明细
     */
    @OneToMany(mappedBy = "order")
    @Cascade(CascadeType.ALL)
    public List<StockOrderItem> getItems() {
        return items;
    }

    public void setItems(List<StockOrderItem> items) {
        if (items == null)
            throw new NullPointerException("items");
        this.items = items;
    }

    /**
     * 参考库存作业（可选）
     */
    public Long getJobRef() {
        return jobRef;
    }

    public void setJobRef(Long jobRef) {
        this.jobRef = jobRef;
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
        if (jobRef == null)
            throw new IllegalStateException("没有指定上层的库存作业，创建对等单据没有意义。");
        StockOrder peer = new StockOrder();
        peer.base = base;
        peer.initTarget = initTarget;
        peer.jobRef = jobRef;
        peer.serial = serial; // DUPLICATED?
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
