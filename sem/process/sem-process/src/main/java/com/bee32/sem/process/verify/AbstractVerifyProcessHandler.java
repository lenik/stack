package com.bee32.sem.process.verify;

public abstract class AbstractVerifyProcessHandler
        implements IVerifyProcessHandler {

    @Override
    public VerifyResult getPreVerifiedResult() {
        return null;
    }

    @Override
    public void preVerify() {
    }

    @Override
    public void handleVerifyResult(VerifyResult result) {
    }

    @Override
    public void preUpdate() {
    }

    @Override
    public void postUpdate() {
    }

}
