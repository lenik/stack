package com.bee32.sem.term;

public class NoSuchTermProviderException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoSuchTermProviderException() {
        super();
    }

    public NoSuchTermProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchTermProviderException(String message) {
        super(message);
    }

    public NoSuchTermProviderException(Throwable cause) {
        super(cause);
    }

}
