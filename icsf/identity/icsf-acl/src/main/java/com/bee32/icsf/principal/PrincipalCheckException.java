package com.bee32.icsf.principal;

import com.bee32.icsf.InternalSecurityException;

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
