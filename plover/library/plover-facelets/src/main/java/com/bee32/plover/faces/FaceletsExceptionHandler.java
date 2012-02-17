package com.bee32.plover.faces;

import com.bee32.plover.arch.exception.AbstractExceptionHandler;
import com.bee32.plover.arch.exception.ExceptionHandleResult;
import com.bee32.plover.faces.utils.FacesUILogger;

public abstract class FaceletsExceptionHandler
        extends AbstractExceptionHandler {

    public FacesUILogger getUILogger() {
        return new FacesUILogger(false);
    }

    @Override
    public final ExceptionHandleResult handle(Throwable exception, Object context) {
        FaceletExceptionContext feCtx = (FaceletExceptionContext) context;
        return handle(exception, feCtx);
    }

    public abstract ExceptionHandleResult handle(Throwable exception, FaceletExceptionContext context);

}
