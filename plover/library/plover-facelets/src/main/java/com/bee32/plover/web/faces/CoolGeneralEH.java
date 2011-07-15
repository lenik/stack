package com.bee32.plover.web.faces;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;

/**
 * TODO Colorize the stack trace.
 */
public class CoolGeneralEH
        extends PloverExceptionHandler {

    public CoolGeneralEH(ExceptionHandler handler) {
        super(handler);
    }

    @Override
    public void handle()
            throws FacesException {
        super.handle();
    }

}
