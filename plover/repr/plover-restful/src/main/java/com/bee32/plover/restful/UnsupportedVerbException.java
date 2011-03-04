package com.bee32.plover.restful;

public class UnsupportedVerbException
        extends RestfulException {

    private static final long serialVersionUID = 1L;

    public UnsupportedVerbException() {
        super();
    }

    public UnsupportedVerbException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedVerbException(String message) {
        super(message);
    }

    public UnsupportedVerbException(Throwable cause) {
        super(cause);
    }

}
