package com.bee32.sem.frame.ui;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.context.FacesContext;

import org.apache.myfaces.el.unified.ResolverBuilderForFaces;

public class LocalELContext
        extends ELContext {

    ELContext parent;
    LocalVariableMapper localVars;

    public LocalELContext(ELContext parent) {
        if (parent == null)
            throw new NullPointerException("parent");
        this.parent = parent;
        this.localVars = new LocalVariableMapper(this);
    }

    public ELContext getParent() {
        return parent;
    }

    public void setParent(ELContext parent) {
        this.parent = parent;
    }

    /**
     * @see ResolverBuilderForFaces
     */
    @Override
    public ELResolver getELResolver() {
        return parent.getELResolver();
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return parent.getFunctionMapper();
    }

    @Override
    public VariableMapper getVariableMapper() {
        return localVars;
    }

    public Object evaluate(ValueExpression expr) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        return evaluate(elContext, expr);
    }

    public Object evaluate(ELContext parent, ValueExpression expr) {
        setParent(parent);
        Object value = expr.getValue(this);
        return value;
    }

}
