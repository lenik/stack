package com.bee32.sem.process.verify.service;

import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.VerifyEvalState;
import com.bee32.sem.process.verify.dto.IVerifiableDto;
import com.bee32.sem.process.verify.dto.VerifyContextDto;

public class VerifyUtils {

    public static boolean isVerified(IVerifiable<?> verifiable) {
        IVerifyContext verifyContext = verifiable.getVerifyContext();
        VerifyEvalState evalState = verifyContext.getVerifyEvalState();
        return VerifyEvalState.VERIFIED.equals(evalState);
    }

    public static boolean isVerified(IVerifiableDto verifiable) {
        VerifyContextDto<?> verifyContext = verifiable.getVerifyContext();
        VerifyEvalState evalState = verifyContext.getVerifyEvalState();
        return VerifyEvalState.VERIFIED.equals(evalState);
    }

}
