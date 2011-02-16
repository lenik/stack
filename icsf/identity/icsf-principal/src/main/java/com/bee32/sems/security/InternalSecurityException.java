package com.bee32.sems.security;

public class InternalSecurityException
        // extends java.lang.SecurityException {
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InternalSecurityException() {
        super();
    }

    public InternalSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalSecurityException(String message) {
        super(message);
    }

    public InternalSecurityException(Throwable cause) {
        super(cause);
    }

}
