package com.bee32.plover.orm.ext.util;

import javax.free.IllegalUsageException;

public class NoSuchEnumException
        extends IllegalUsageException {

    private static final long serialVersionUID = 1L;

    public NoSuchEnumException(Class<?> enumType, int altId) {
        super("No such enum with alt-id = " + altId + " (hex: " + Integer.toHexString(altId) + ")");
    }

    public NoSuchEnumException(Class<?> enumType, String altName) {
        super("No such enum with alt-name= " + altName);
    }

}
