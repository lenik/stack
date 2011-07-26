package com.bee32.plover.orm.entity;

public class NonUniqueException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NonUniqueException() {
        super();
    }

    public NonUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonUniqueException(String message) {
        super(message);
    }

    public NonUniqueException(Throwable cause) {
        super(cause);
    }

}
