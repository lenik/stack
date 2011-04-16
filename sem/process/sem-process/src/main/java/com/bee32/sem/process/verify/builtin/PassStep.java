package com.bee32.sem.process.verify.builtin;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

/**
 * 审核步骤
 */
@Entity
public class PassStep
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    private PassToNext policy;
    private int order;
    private Principal responsible;
    public boolean optional;

    public PassStep() {
    }

    public PassStep(PassToNext policy, int order, Principal responsible, boolean optional) {
        if (policy == null)
            throw new NullPointerException("policy");
        if (responsible == null)
            throw new NullPointerException("responsible");

        this.policy = policy;
        this.order = order;
        this.responsible = responsible;
        this.optional = optional;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public PassToNext getPolicy() {
        return policy;
    }

    public void setPolicy(PassToNext policy) {
        if (policy == null)
            throw new NullPointerException("policy");
        this.policy = policy;
    }

    /**
     * 步骤顺序
     */
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 责任人
     */
    @ManyToOne(optional = false)
    public Principal getResponsible() {
        return responsible;
    }

    public void setResponsible(Principal responsible) {
        this.responsible = responsible;
    }

    /**
     * 步骤可选
     */
    // @Column(nullable = false)
    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        if (optional)
            out.print(".");
        out.print(order);
        out.print(" - ");
        out.print(responsible);
    }

}
