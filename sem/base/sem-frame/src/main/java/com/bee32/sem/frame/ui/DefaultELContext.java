package com.bee32.sem.frame.ui;

import javax.el.BeanELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;

import org.apache.myfaces.view.facelets.el.DefaultFunctionMapper;
import org.apache.myfaces.view.facelets.el.DefaultVariableMapper;

public class DefaultELContext
        extends ELContext {

    ELResolver elResolver = new BeanELResolver();
    FunctionMapper functionMapper = new DefaultFunctionMapper();
    VariableMapper variableMapper = new DefaultVariableMapper();

    @Override
    public ELResolver getELResolver() {
        return elResolver;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variableMapper;
    }

    public static DefaultELContext instance;

    public static ELContext getInstance() {
        if (instance == null) {
            synchronized (DefaultELContext.class) {
                if (instance == null) {
                    instance = new DefaultELContext();
                }
            }
        }
        return instance;
    }

}
