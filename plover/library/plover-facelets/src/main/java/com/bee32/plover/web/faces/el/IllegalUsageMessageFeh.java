package com.bee32.plover.web.faces.el;

import javax.free.IdentifiedException;
import javax.free.IllegalUsageException;

import com.bee32.plover.web.faces.AnnotatedFeh;
import com.bee32.plover.web.faces.ExceptionHandleResult;
import com.bee32.plover.web.faces.FaceletExceptionContext;
import com.bee32.plover.web.faces.ForException;
import com.bee32.plover.web.faces.utils.FacesUILogger;

@ForException(IllegalUsageException.class)
public class IllegalUsageMessageFeh
        extends AnnotatedFeh {

    @Override
    public ExceptionHandleResult handle(FaceletExceptionContext context, Throwable exception) {
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
