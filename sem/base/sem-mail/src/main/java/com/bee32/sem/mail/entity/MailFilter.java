package com.bee32.sem.mail.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.plover.ox1.color.Green;
import com.bee32.plover.ox1.color.UIEntityAuto;

/**
 * 邮件过滤器
 *
 * 用于对接收到的邮件进行自动分拣和处理。
 */
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

    @Override
    public void populate(Object source) {
        if (source instanceof MailFilter)
            _populate((MailFilter) source);
        else
            super.populate(source);
    }

    protected void _populate(MailFilter o) {
        super._populate(o);
        enabled = o.enabled;
        order = o.order;
        expr = o.expr;
        source = o.source;
        target = o.target;
        transferTo = o.transferTo;
        chMask = o.chMask;
        chBits = o.chBits;
    }

    /**
     * 启用标志
     *
     * 表示过滤器是否被启用。
     */
    @Column(nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 次序
     *
     * 多个过滤器的求值次序。
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 表达式
     *
     * 一个逻辑表达式用于指定被选择的邮件。
     */
    @Column(length = EXPR_LENGTH, nullable = false)
    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    /**
     * 源文件夹
     *
     * 作为邮件来源的文件夹。
     */
    @ManyToOne
    public MailFolder getSource() {
        return source;
    }

    public void setSource(MailFolder source) {
        this.source = source;
    }

    /**
     * 目标文件夹
     *
     * 作为处理结果的文件夹。
     */
    @ManyToOne
    public MailFolder getTarget() {
        return target;
    }

    public void setTarget(MailFolder target) {
        this.target = target;
    }

    /**
     * 传输目标
     *
     * 传输到指定的目标。
     */
    @Column(length = TRANSFER_TO_LENGTH)
    public String getTransferTo() {
        return transferTo;
    }

    public void setTransferTo(String transferTo) {
        this.transferTo = transferTo;
    }

    /**
     * 变更掩码
     *
     * 说明变更的位组掩码。
     */
    @Column(nullable = false)
    public int getChMask() {
        return chMask;
    }

    public void setChMask(int chMask) {
        this.chMask = chMask;
    }

    /**
     * 变更位组
     *
     * 说明变更的位组。
     */
    @Column(nullable = false)
    public int getChBits() {
        return chBits;
    }

    public void setChBits(int chBits) {
        this.chBits = chBits;
    }

}
