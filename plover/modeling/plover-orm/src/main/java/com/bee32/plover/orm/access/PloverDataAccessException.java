package com.bee32.plover.orm.access;

import org.springframework.dao.DataAccessException;

public class PloverDataAccessException
        extends DataAccessException {

    private static final long serialVersionUID = 1L;

    public PloverDataAccessException() {
        super(null);
    }

    public PloverDataAccessException(String msg) {
        super(msg);
    }

    public PloverDataAccessException(Throwable cause) {
        super(null, cause);
    }

    public PloverDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
