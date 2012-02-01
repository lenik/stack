package com.bee32.plover.faces;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class PloverExceptionHandlerFactory
        extends ExceptionHandlerFactory {

    private ExceptionHandlerFactory parent;

    public PloverExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler result = parent.getExceptionHandler();
        result = new PloverExceptionHandler(result);
        return result;
    }
}
