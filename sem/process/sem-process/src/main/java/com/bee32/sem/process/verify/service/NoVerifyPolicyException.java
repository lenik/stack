package com.bee32.sem.process.verify.service;

import com.bee32.sem.process.verify.VerifyException;

/**
 * 没有可用的审核策略异常
 *
 * <p lang="en">
 * No verify policy exception
 */
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
