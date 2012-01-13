package com.bee32.sem.process.verify;

public interface IVerifyProcessHandler {

    VerifyResult getPreVerifiedResult();

    void preVerify();

    void handleVerifyResult(VerifyResult result);

    void preUpdate();

    void postUpdate();

}
