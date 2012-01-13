package com.bee32.plover.web.faces;

import com.bee32.plover.inject.ServiceTemplate;
import com.bee32.plover.web.faces.utils.FacesUILogger;

/**
 * You must declare {@link ForException} annotation.
 */
@ServiceTemplate
// Must be eager, so as to inject to PloverExceptionHandler.handlerMap as soon as possible.
// @Lazy
public abstract class AnnotatedFeh
        implements IFaceletExceptionHandler {

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
        return new FacesUILogger(false);
    }

}
