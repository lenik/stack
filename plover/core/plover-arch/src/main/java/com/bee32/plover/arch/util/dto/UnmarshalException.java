package com.bee32.plover.arch.util.dto;

public class UnmarshalException
        extends MarshalException {

    private static final long serialVersionUID = 1L;

    public UnmarshalException() {
        super();
    }

    public UnmarshalException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmarshalException(String message) {
        super(message);
    }

    public UnmarshalException(Throwable cause) {
        super(cause);
    }

}
