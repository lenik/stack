package com.bee32.sem.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
@Green
@SequenceGenerator(name = "idgen", sequenceName = "mail_filter_seq", allocationSize = 1)
public class MailFilter
        extends UIEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    public static final int EXPR_LENGTH = 1000;
    public static final int TRANSFER_TO_LENGTH = 50;

    boolean enabled;
    int order;

    String expr;

    MailFolder source;
    MailFolder target;
    String transferTo;

    int chMask;
    int chBits;

X-Population

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

    @Column(length = EXPR_LENGTH, nullable = false)
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

    @Column(length = TRANSFER_TO_LENGTH)
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
