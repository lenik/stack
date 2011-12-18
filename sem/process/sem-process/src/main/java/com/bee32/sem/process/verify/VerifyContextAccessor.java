package com.bee32.sem.process.verify;

import java.util.Date;


public class VerifyContextAccessor {

    public static void setVerifyState(AbstractVerifyContext vc, VerifyState verifyState) {
        vc.setVerifyState(verifyState);
    }

    public static void setVerifyError(AbstractVerifyContext vc, String error) {
        vc.setVerifyError(error);
    }

    public static void setVerifyEvalDate(AbstractVerifyContext vc, Date evalDate) {
        vc.setVerifyEvalDate(evalDate);
    }

}
