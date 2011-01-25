package com.bee32.plover.cache;

public class CacheRetrieveException
        extends CacheException {

    private static final long serialVersionUID = 1L;

    public CacheRetrieveException() {
        super();
    }

    public CacheRetrieveException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheRetrieveException(String message) {
        super(message);
    }

    public CacheRetrieveException(Throwable cause) {
        super(cause);
    }

}
