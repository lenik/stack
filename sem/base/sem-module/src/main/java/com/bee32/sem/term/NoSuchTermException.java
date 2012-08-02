package com.bee32.sem.term;

public class NoSuchTermException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoSuchTermException() {
        super();
    }

    public NoSuchTermException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTermException(String message) {
        super(message);
    }

    public NoSuchTermException(Throwable cause) {
        super(cause);
    }

}
