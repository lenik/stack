package com.bee32.icsf;

/**
 * 内部安全异常
 */
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
