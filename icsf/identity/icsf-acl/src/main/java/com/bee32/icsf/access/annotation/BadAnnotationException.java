package com.bee32.icsf.access.annotation;

public class BadAnnotationException
        extends IllegalArgumentException {

    private static final long serialVersionUID = 1L;

    public BadAnnotationException() {
        super();
    }

    public BadAnnotationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadAnnotationException(String s) {
        super(s);
    }

    public BadAnnotationException(Throwable cause) {
        super(cause);
    }

}
