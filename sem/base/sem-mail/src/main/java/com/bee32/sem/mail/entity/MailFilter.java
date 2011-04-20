package com.bee32.sem.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.bee32.plover.orm.entity.EntityBean;

@Entity
public class MailFilter
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    String name;
    String description;

    boolean enabled;
    int order;

    String expr;

    MailBox source;
    MailBox target;
    String transferTo;

    int chMask;
    int chBits;

    @Column(length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Column(length = 1000, nullable = false)
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    @ManyToOne
    public MailBox getSource() {
        return source;
    }

    public void setSource(MailBox source) {
        this.source = source;
    }

    @ManyToOne
    public MailBox getTarget() {
        return target;
    }

    public void setTarget(MailBox target) {
        this.target = target;
    }

    @Column(length = 50)
    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    @Column(nullable = false)
    public int getChMask() {
        return chMask;
    }

    public void setChMask(int chMask) {
        this.chMask = chMask;
    }

    @Column(nullable = false)
    public int getChBits() {
        return chBits;
    }

    public void setChBits(int chBits) {
        this.chBits = chBits;
    }

}
