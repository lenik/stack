package com.bee32.plover.cache;

public class CacheCheckException
        extends CacheException {

    private static final long serialVersionUID = 1L;

    public CacheCheckException() {
        super();
    }

    public CacheCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheCheckException(String message) {
        super(message);
    }

    public CacheCheckException(Throwable cause) {
        super(cause);
    }

}
