package com.bee32.sem.process.verify;

import com.bee32.sem.process.ProcessSecurityException;

public class VerifyException
        extends ProcessSecurityException {

    private static final long serialVersionUID = 1L;

    public VerifyException() {
        super();
    }

    public VerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyException(String message) {
        super(message);
    }

    public VerifyException(Throwable cause) {
        super(cause);
    }

}
