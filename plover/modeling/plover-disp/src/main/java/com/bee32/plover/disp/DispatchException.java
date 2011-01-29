package com.bee32.plover.disp;

public class DispatchException
        extends Exception {

    private static final long serialVersionUID = 1L;

    public DispatchException() {
        super();
    }

    public DispatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public DispatchException(String message) {
        super(message);
    }

    public DispatchException(Throwable cause) {
        super(cause);
    }

}
