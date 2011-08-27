package com.bee32.sem.process.verify.builtin;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.bee32.icsf.principal.Principal;
import com.bee32.plover.orm.ext.c.CEntityAuto;
import com.bee32.plover.orm.ext.color.Blue;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

/**
 * 审核步骤
 */
@Entity
@Blue
@SequenceGenerator(name = "idgen", sequenceName = "pass_step_seq", allocationSize = 1)
public class PassStep
        extends CEntityAuto<Integer> {

    private static final long serialVersionUID = 1L;

    private PassToNextPolicy policy;

    public boolean optional;
    private int order;

    private Principal responsible;

    public PassStep() {
    }

    public PassStep(PassToNextPolicy policy, int order, Principal responsible, boolean optional) {
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
    public PassToNextPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(PassToNextPolicy policy) {
        if (policy == null)
            throw new NullPointerException("policy");
        this.policy = policy;
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

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        if (optional)
            out.print(".");
        out.print(order);
        out.print(" - ");
        out.print(responsible);
    }

}
