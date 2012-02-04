package com.bee32.sem.frame.ui;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class LocalVariableMapper
        extends VariableMapper {

    LocalELContext localELContext;
    Map<String, ValueExpression> vars;

    public LocalVariableMapper(LocalELContext localELContext) {
        this.localELContext = localELContext;
        this.vars = new HashMap<String, ValueExpression>();
    }

    ELContext getParentContext() {
        return localELContext.getParent();
    }

    @Override
    public ValueExpression resolveVariable(String variable) {
        ValueExpression expr = vars.get(variable);
        if (expr == null) {
            ELContext parent = getParentContext();
            if (parent != null)
                expr = parent.getVariableMapper().resolveVariable(variable);
        }
        return expr;
    }

    @Override
    public ValueExpression setVariable(String variable, ValueExpression expression) {
        ValueExpression old = vars.put(variable, expression);
        if (old == null) {
            ELContext parent = getParentContext();
            if (parent != null) {
                VariableMapper parentVars = parent.getVariableMapper();
                if (parentVars != null)
                    old = parentVars.resolveVariable(variable);
            }
        }
        return old;
    }

    public void removeVariable(String variable) {
        vars.remove(variable);
    }

}
