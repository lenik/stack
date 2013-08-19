package com.bee32.sem.process;

/**
 * 过程安全异常
 *
 * <p lang="en">
 */
public class ProcessSecurityException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public ProcessSecurityException() {
        super();
    }

    public ProcessSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProcessSecurityException(String message) {
        super(message);
    }

    public ProcessSecurityException(Throwable cause) {
        super(cause);
    }

}
