package com.bee32.sem.process.verify.util;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.VerifyEvalState;

public class VerifyCriteria
        extends CriteriaSpec {

    static String verifyEvalStateProperty = "verifyContext._verifyEvalState";

    @LeftHand(IVerifiable.class)
    public static CriteriaElement verified() {
        return equals(verifyEvalStateProperty, VerifyEvalState.VERIFIED.getValue());
    }

    @LeftHand(IVerifiable.class)
    public static CriteriaElement verified(String property) {
        return equals(property + "." + verifyEvalStateProperty, VerifyEvalState.VERIFIED.getValue());
    }

}
