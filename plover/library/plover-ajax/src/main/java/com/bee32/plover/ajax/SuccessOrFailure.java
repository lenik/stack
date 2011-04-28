package com.bee32.plover.ajax;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public abstract class SuccessOrFailure {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "success";
    public static final String FAILURE = "fail";

    private Object result;
    private String message;
    private transient Throwable exception;

    public SuccessOrFailure() {
        this.result = SUCCESS;
    }

    public SuccessOrFailure(String successMessage) {
        this.result = SUCCESS;
        this.message = successMessage;
    }

    public SuccessOrFailure(Throwable exception) {
        this.result = FAILURE;
        this.message = exception.getMessage();
        this.exception = exception;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
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

    protected abstract void service()
            throws Exception;

    public String dump() {
        try {
            service();
        } catch (Exception e) {
            message = "通用保护错误：" + e.getMessage() + " (" + e.getClass().getName() + ")";
            exception = e;
        }

        String json = JsonUtil.dump(SuccessOrFailure.class, this);
        return json;
    }

    public <T> T jsonDump(HttpServletResponse resp)
            throws IOException {

        try {
            service();
        } catch (Exception e) {
            message = e.getMessage();
            exception = e;
            e.printStackTrace();
        }

        JsonUtil.dump(resp, SuccessOrFailure.class, this);
        return null;
    }

}
