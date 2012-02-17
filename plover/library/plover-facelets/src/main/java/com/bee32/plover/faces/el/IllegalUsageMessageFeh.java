package com.bee32.plover.faces.el;

import javax.free.IdentifiedException;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.exception.ExceptionHandleResult;
import com.bee32.plover.arch.exception.ForException;
import com.bee32.plover.faces.FaceletExceptionContext;
import com.bee32.plover.faces.FaceletsExceptionHandler;
import com.bee32.plover.faces.utils.FacesUILogger;

@ForException(IllegalUsageException.class)
public class IllegalUsageMessageFeh
        extends FaceletsExceptionHandler {

    @Override
    public ExceptionHandleResult handle(Throwable exception, FaceletExceptionContext context) {
        exception.printStackTrace();
        Throwable elCause = exception.getCause();
        if (elCause instanceof IllegalArgumentException) {
            // elCause.getStackTrace()...
            getUILogger();
        } else if (elCause instanceof IdentifiedException) {
            FacesUILogger logger = getUILogger();
            logger.error("提前检查错误", exception);
        }
        return handled();
    }

}
