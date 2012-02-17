package com.bee32.plover.arch.exception;

public class ExceptionHandleResult {

    public static final int TYPE_SKIP = 0;
    public static final int TYPE_HANDLED = 1;
    public static final int TYPE_REDIRECT = 2;

    int type;
    Object value;

    public ExceptionHandleResult(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public static final ExceptionHandleResult SKIP = new ExceptionHandleResult(TYPE_SKIP, null);
    public static final ExceptionHandleResult HANDLED = new ExceptionHandleResult(TYPE_HANDLED, null);

}
