package com.bee32.plover.faces.view;

import javax.faces.application.ViewExpiredException;

import com.bee32.plover.arch.exception.ExceptionHandleResult;
import com.bee32.plover.arch.exception.ForException;
import com.bee32.plover.faces.FaceletExceptionContext;
import com.bee32.plover.faces.FaceletsExceptionHandler;
import com.bee32.plover.faces.utils.FacesUILogger;

@ForException(value = ViewExpiredException.class, fullStackSearch = true)
public class ViewExpiredFeh
        extends FaceletsExceptionHandler {

    @Override
    public ExceptionHandleResult handle(Throwable exception, FaceletExceptionContext context) {
        FacesUILogger uiLogger = getUILogger();
        uiLogger.error("页面已过期，请重新载入页面后重试。");
        return ExceptionHandleResult.HANDLED;
    }

}
