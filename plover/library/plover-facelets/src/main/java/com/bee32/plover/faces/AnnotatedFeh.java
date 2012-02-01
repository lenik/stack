package com.bee32.plover.faces;

import com.bee32.plover.faces.utils.FacesUILogger;
import com.bee32.plover.inject.ServiceTemplate;

/**
 * You must declare {@link ForException} annotation.
 */
@ServiceTemplate
// Must be eager, so as to inject to PloverExceptionHandler.handlerMap as soon as possible.
// @Lazy
public abstract class AnnotatedFeh
        implements IFaceletExceptionHandler {

    boolean fullStackSearch;

    public AnnotatedFeh() {
        ForException annotation = getClass().getAnnotation(ForException.class);
        if (annotation != null)
            fullStackSearch = annotation.fullStackSearch();
    }

    @Override
    public int getPriority() {
        return 0;
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
        return new FacesUILogger(false);
    }

    @Override
    public boolean isFullStackSearch() {
        return fullStackSearch;
    }

}
