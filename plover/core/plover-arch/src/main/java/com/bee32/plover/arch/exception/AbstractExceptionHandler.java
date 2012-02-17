package com.bee32.plover.arch.exception;

import com.bee32.plover.inject.ServiceTemplate;

/**
 * You must declare {@link ForException} annotation.
 */
@ServiceTemplate
// Must be eager, so as to inject to PloverExceptionHandler.handlerMap as soon as possible.
// @Lazy
public abstract class AbstractExceptionHandler
        implements IExceptionHandler {

    boolean fullStackSearch;

    public AbstractExceptionHandler() {
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

    @Override
    public boolean isFullStackSearch() {
        return fullStackSearch;
    }

}
