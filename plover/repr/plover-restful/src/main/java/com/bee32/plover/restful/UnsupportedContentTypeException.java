package com.bee32.plover.restful;

public class UnsupportedContentTypeException
        extends RestfulException {

    private static final long serialVersionUID = 1L;

    public UnsupportedContentTypeException() {
        super();
    }

    public UnsupportedContentTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedContentTypeException(String message) {
        super(message);
    }

    public UnsupportedContentTypeException(Throwable cause) {
        super(cause);
    }

}
