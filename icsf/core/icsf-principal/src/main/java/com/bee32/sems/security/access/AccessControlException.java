package com.bee32.sems.security.access;

import com.bee32.sems.security.InternalSecurityException;

public class AccessControlException
        extends InternalSecurityException {

    private static final long serialVersionUID = 1L;

    public AccessControlException() {
        super();
    }

    public AccessControlException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessControlException(String message) {
        super(message);
    }

    public AccessControlException(Throwable cause) {
        super(cause);
    }

}
