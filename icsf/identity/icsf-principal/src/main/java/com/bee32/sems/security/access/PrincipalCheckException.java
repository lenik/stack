package com.bee32.sems.security.access;

import com.bee32.sems.security.InternalSecurityException;

public class PrincipalCheckException
        extends InternalSecurityException {

    private static final long serialVersionUID = 1L;

    public PrincipalCheckException() {
        super();
    }

    public PrincipalCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrincipalCheckException(String message) {
        super(message);
    }

    public PrincipalCheckException(Throwable cause) {
        super(cause);
    }

}
