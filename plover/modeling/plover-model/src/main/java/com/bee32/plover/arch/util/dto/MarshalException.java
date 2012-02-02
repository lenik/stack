package com.bee32.plover.arch.util.dto;

public class MarshalException
        extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MarshalException() {
        super();
    }

    public MarshalException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarshalException(String message) {
        super(message);
    }

    public MarshalException(Throwable cause) {
        super(cause);
    }

}
