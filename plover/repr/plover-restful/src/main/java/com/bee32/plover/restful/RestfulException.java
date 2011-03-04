package com.bee32.plover.restful;

public class RestfulException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public RestfulException() {
        super();
    }

    public RestfulException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestfulException(String message) {
        super(message);
    }

    public RestfulException(Throwable cause) {
        super(cause);
    }

}
