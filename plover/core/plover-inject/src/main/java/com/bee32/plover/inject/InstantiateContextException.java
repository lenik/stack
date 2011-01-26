package com.bee32.plover.inject;

public class InstantiateContextException
        extends ContextException {

    private static final long serialVersionUID = 1L;

    public InstantiateContextException() {
        super();
    }

    public InstantiateContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstantiateContextException(String message) {
        super(message);
    }

    public InstantiateContextException(Throwable cause) {
        super(cause);
    }

}
