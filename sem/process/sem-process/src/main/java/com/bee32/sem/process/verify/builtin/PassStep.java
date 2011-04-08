package com.bee32.sem.process.verify.builtin;

import com.bee32.icsf.principal.IPrincipal;
import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.util.FormatStyle;
import com.bee32.plover.util.PrettyPrintStream;

/**
 * 审核步骤
 */
public class PassStep
        extends EntityBean<Integer> {

    private static final long serialVersionUID = 1L;

    /** 责任人 */
    private IPrincipal responsible;

    /** 步骤可选 */
    public boolean optional;

    public PassStep(IPrincipal responsible, boolean optional) {
        if (responsible == null)
            throw new NullPointerException("responsible");
        this.optional = optional;
    }

    public IPrincipal getResponsible() {
        return responsible;
    }

    public void setResponsible(IPrincipal responsible) {
        this.responsible = responsible;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public void toString(PrettyPrintStream out, FormatStyle format) {
        out.print(responsible);
        if (optional)
            out.print("?");
    }

}
