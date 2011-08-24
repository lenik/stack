package com.bee32.sem.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.orm.ext.color.Green;
import com.bee32.plover.orm.ext.color.UIEntityAuto;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "mail_filter_seq", allocationSize = 1)
public class MailFilter
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    boolean enabled;
    int order;

    String expr;

    MailFolder source;
    MailFolder target;
    String transferTo;

    int chMask;
    int chBits;

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
    public MailFolder getSource() {
        return source;
    }

    public void setSource(MailFolder source) {
        this.source = source;
    }

    @ManyToOne
    public MailFolder getTarget() {
        return target;
    }

    public void setTarget(MailFolder target) {
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
