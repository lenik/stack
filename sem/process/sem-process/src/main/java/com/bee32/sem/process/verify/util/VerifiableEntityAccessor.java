package com.bee32.sem.process.verify.util;

import java.util.Date;

import com.bee32.sem.process.verify.VerifyState;

public class VerifiableEntityAccessor {

    public static void setVerifyState(AbstractVerifyContext<?, ?> entity, VerifyState verifyState) {
        entity.setVerifyState(verifyState);
    }

    public static void setVerifyError(AbstractVerifyContext<?, ?> entity, String error) {
        entity.setVerifyError(error);
    }

    public static void setVerifyEvalDate(AbstractVerifyContext<?, ?> entity, Date evalDate) {
        entity.setVerifyEvalDate(evalDate);
    }

}
