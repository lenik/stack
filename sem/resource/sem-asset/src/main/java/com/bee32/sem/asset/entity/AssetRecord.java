package com.bee32.sem.asset.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.ox1.principal.User;
import com.bee32.sem.base.tx.TxEntity;
import com.bee32.sem.people.entity.Party;
import com.bee32.sem.world.monetary.MCValue;

@Entity
public class AssetRecord
        extends TxEntity {

    private static final long serialVersionUID = 1L;

    public static final int ORDER_SERIAL_LENGTH = 30;

    AssetSubject subject; // =AssetSubject.XXX;
    Party op;
    User opUser;
    Party peer;
    User peerUser;
    MCValue value = new MCValue();
    Payment payment;
    String orderSerial;
    BankAccount bankAccount;

    @ManyToOne(optional = false)
    public AssetSubject getSubject() {
        return subject;
    }

    public void setSubject(AssetSubject subject) {
        if (subject == null)
            throw new NullPointerException("subject");
        this.subject = subject;
    }

    @ManyToOne(optional = false)
    public Party getOp() {
        return op;
    }

    public void setOp(Party op) {
        if (op == null)
            throw new NullPointerException("op");
        this.op = op;
    }

    @ManyToOne
    public User getOpUser() {
        return opUser;
    }

    public void setOpUser(User opUser) {
        this.opUser = opUser;
    }

    @ManyToOne
    public Party getPeer() {
        return peer;
    }

    public void setPeer(Party peer) {
        this.peer = peer;
    }

    @ManyToOne
    public User getPeerUser() {
        return peerUser;
    }

    public void setPeerUser(User peerUser) {
        this.peerUser = peerUser;
    }

    @Embedded
    // @AttributeOverrides({
    // // { price_c, price }
    // @AttributeOverride(name = "currency", column = @Column(name = "priceC")), //
    // @AttributeOverride(name = "value", column = @Column(name = "price")) })
    public MCValue getValue() {
        return value;
    }

    public void setValue(MCValue value) {
        if (value == null)
            throw new NullPointerException("value");
        this.value = value;
    }

    @ManyToOne
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Column(length = ORDER_SERIAL_LENGTH)
    public String getOrderSerial() {
        return orderSerial;
    }

    public void setOrderSerial(String orderSerial) {
        this.orderSerial = orderSerial;
    }

    @ManyToOne
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

}
