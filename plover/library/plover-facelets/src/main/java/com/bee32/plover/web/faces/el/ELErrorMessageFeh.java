package com.bee32.plover.web.faces.el;

import javax.el.ELException;
import javax.free.IdentifiedException;

import com.bee32.plover.web.faces.AnnotatedFaceletExceptionHandler;
import com.bee32.plover.web.faces.ExceptionHandleResult;
import com.bee32.plover.web.faces.FaceletExceptionContext;
import com.bee32.plover.web.faces.ForException;
import com.bee32.plover.web.faces.utils.FacesUILogger;

@NotAComponent
@ForException(ELException.class)
public class ELErrorMessageFeh
        extends AnnotatedFaceletExceptionHandler {

    @Override
    public ExceptionHandleResult handle(FaceletExceptionContext context, Throwable exception) {
        exception.printStackTrace();
        Throwable elCause = exception.getCause();
        if (elCause instanceof IllegalArgumentException) {
            // elCause.getStackTrace()...
            getUILogger();
        } else if (elCause instanceof IdentifiedException) {
            FacesUILogger logger = getUILogger();
            logger.error(exception.getMessage());
        }
        return handled();
    }

}
