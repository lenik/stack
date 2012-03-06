package com.bee32.plover.ox1.formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.ox1.color.UIEntityAuto;

@Entity
public class Formula
        extends UIEntityAuto<Integer>
        implements ITypeAbbrAware {

    private static final long serialVersionUID = 1L;

    public static final int FORMULA_LENGTH = 1024;

    IFormulaContext context;
    String formula;

    @Override
    public void populate(Object source) {
        if (source instanceof Formula)
            _populate((Formula) source);
        else
            super.populate(source);
    }

    protected void _populate(Formula o) {
        super._populate(o);
        context = o.context;
        formula = o.formula;
    }

    @Transient
    public IFormulaContext getContext() {
        return context;
    }

    public void setContext(IFormulaContext context) {
        this.context = context;
    }

    @Column(length = ABBR_LEN, nullable = false)
    String getContextId() {
        String contextId = ABBR.abbr(context.getClass());
        return contextId;
    }

    void setContextId(String contextId)
            throws ClassNotFoundException {
        Class<?> contextClass = ABBR.expand(contextId);
        context = FormulaContextManager.getFormulaContext(contextClass);
    }

    @Column(length = FORMULA_LENGTH, nullable = false)
    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        if (formula == null)
            throw new NullPointerException("formula");
        this.formula = formula;
    }

}
