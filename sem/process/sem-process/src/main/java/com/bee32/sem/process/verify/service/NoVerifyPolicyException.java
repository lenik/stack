package com.bee32.sem.process.verify.service;

import com.bee32.sem.process.verify.VerifyException;

public class NoVerifyPolicyException
        extends VerifyException {

    private static final long serialVersionUID = 1L;

    public NoVerifyPolicyException() {
        super();
    }

    public NoVerifyPolicyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoVerifyPolicyException(String message) {
        super(message);
    }

    public NoVerifyPolicyException(Throwable cause) {
        super(cause);
    }

}
