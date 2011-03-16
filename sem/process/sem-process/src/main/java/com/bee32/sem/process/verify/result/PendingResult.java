package com.bee32.sem.process.verify.result;

public class PendingResult
        extends ErrorResult {

    public PendingResult() {
        super("尚未审核。");
    }

    public PendingResult(String message) {
        super(message);
    }

    static final PendingResult instance = new PendingResult();

    public static PendingResult getInstance() {
        return instance;
    }

}
