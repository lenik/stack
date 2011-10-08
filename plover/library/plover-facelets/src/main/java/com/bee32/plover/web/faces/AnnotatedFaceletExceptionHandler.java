package com.bee32.plover.web.faces;

import javax.free.IllegalUsageException;

import com.bee32.plover.inject.ComponentTemplate;
import com.bee32.plover.web.faces.utils.FacesUILogger;

/**
 * You must declare {@link ForException} annotation.
 */
@ComponentTemplate
// Must be eager, so as to inject to PloverExceptionHandler.handlerMap as soon as possible.
// @Lazy
public abstract class AnnotatedFaceletExceptionHandler
        implements IFaceletExceptionHandler {

    /**
     * Auto discovery and configure here.
     */
    public AnnotatedFaceletExceptionHandler() {

        ForException annotation = getClass().getAnnotation(ForException.class);
        if (annotation == null)
            throw new IllegalUsageException("No annotation " + ForException.class + " is declared on " + getClass());

        Class<? extends Throwable> exceptionType = annotation.value();

        PloverExceptionHandler.register(exceptionType, this);
    }

    public ExceptionHandleResult skip() {
        return ExceptionHandleResult.SKIP;
    }

    public ExceptionHandleResult handled() {
        return ExceptionHandleResult.HANDLED;
    }

    public ExceptionHandleResult redirect(String resultView) {
        return new ExceptionHandleResult(ExceptionHandleResult.TYPE_REDIRECT, resultView);
    }

    public FacesUILogger getUILogger() {
        return new FacesUILogger();
    }

}
