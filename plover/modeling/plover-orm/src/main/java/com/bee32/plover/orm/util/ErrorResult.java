package com.bee32.plover.orm.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class ErrorResult
        implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean failed;
    private String message;
    private Throwable exception;

    ErrorResult(boolean failed) {
        this.failed = failed;
    }

    ErrorResult(boolean failed, String message) {
        this.failed = failed;
        this.message = message;
    }

    ErrorResult(Throwable exception) {
        this.failed = true;
        this.message = exception.getMessage();
        this.exception = exception;
    }

    ErrorResult(boolean failed, String message, Throwable exception) {
        this.failed = failed;
        this.message = message;
        this.exception = exception;
    }

    public static final ErrorResult SUCCESS = null;

    public static ErrorResult success(String message) {
        return new ErrorResult(false, message);
    }

    public static ErrorResult error(String message) {
        return new ErrorResult(true, message);
    }

    public static ErrorResult error(String errorMessageFormat, Object... args) {
        return new ErrorResult(true, String.format(errorMessageFormat, args));
    }

    public static ErrorResult error(String message, Throwable exception) {
        return new ErrorResult(true, message, exception);
    }

    public static ErrorResult error(Throwable exception) {
        return new ErrorResult(true, null, exception);
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (failed)
            sb.append("Failed");
        else
            sb.append("No-Error");

        if (message != null) {
            sb.append(": ");
            sb.append(message);
        }

        if (exception != null) {
            sb.append("\nException: ");
            StringWriter buf = new StringWriter();
            exception.printStackTrace(new PrintWriter(buf));
            sb.append(buf);
        }

        return sb.toString();
    }

}
