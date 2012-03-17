package com.bee32.icsf.access;

import com.bee32.plover.faces.AbstractFet;

public class AccessControlExceptionFet
        extends AbstractFet {

    @Override
    public int handle(Throwable exception) {
        if (exception instanceof AccessControlException)
            return REPLACE;
        else
            return SKIP;
    }

    @Override
    public String translate(Throwable exception, String message) {
        AccessControlException e = (AccessControlException) exception;
        return e.getLocalizedMessage();
    }

}
