package com.bee32.sem.process.verify.result;

public class ErrorResult {

    private String message;

    public ErrorResult() {
        this.message = getClass().getName();
    }

    public ErrorResult(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
