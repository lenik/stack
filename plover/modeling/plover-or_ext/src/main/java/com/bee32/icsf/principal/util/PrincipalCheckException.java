package com.bee32.icsf.principal.util;

public class PrincipalCheckException
        extends RuntimeException {

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
