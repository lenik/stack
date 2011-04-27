package com.bee32.sem.process.verify.util;

import com.bee32.sem.process.verify.VerifyState;

public class VerifiableEntityAccessor {

    public static void setVerifyState(VerifiableEntityBean<?, ?> entity, VerifyState verifyState) {
        entity.setVerifyState(verifyState);
    }

    public static void setVerifyError(VerifiableEntityBean<?, ?> entity, String error) {
        entity.setVerifyError(error);
    }

}
