package com.bee32.sems.security;

public class UserSecurityException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public UserSecurityException() {
        super();
    }

    public UserSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserSecurityException(String s) {
        super(s);
    }

    public UserSecurityException(Throwable cause) {
        super(cause);
    }

}
