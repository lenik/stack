package com.bee32.plover.arch.util;

import javax.free.IllegalUsageException;

public class NoSuchEnumException
        extends IllegalUsageException {

    private static final long serialVersionUID = 1L;

    public NoSuchEnumException(Class<?> enumType, int altId) {
        super(enumType + ": No such enum with alt-id = " + altId + " (hex: " + Integer.toHexString(altId) + ")");
    }

    public NoSuchEnumException(Class<?> enumType, String altName) {
        super(enumType + ": No such enum with alt-name= " + altName);
    }

}
