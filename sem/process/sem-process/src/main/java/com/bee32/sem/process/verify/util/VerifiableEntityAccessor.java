package com.bee32.sem.process.verify.util;

import java.util.Date;

import com.bee32.sem.process.verify.VerifyState;

public class VerifiableEntityAccessor {

    public static void setVerifyState(VerifiableEntity<?, ?> entity, VerifyState verifyState) {
        entity.setVerifyState(verifyState);
    }

    public static void setVerifyError(VerifiableEntity<?, ?> entity, String error) {
        entity.setVerifyError(error);
    }

    public static void setVerifyEvalDate(VerifiableEntity<?, ?> entity, Date evalDate) {
        entity.setVerifyEvalDate(evalDate);
    }

}
