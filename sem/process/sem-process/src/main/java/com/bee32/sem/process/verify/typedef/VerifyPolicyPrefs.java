package com.bee32.sem.process.verify.typedef;

import com.bee32.sem.process.verify.IVerifiable;
import com.bee32.sem.process.verify.IVerifyContext;
import com.bee32.sem.process.verify.IVerifyPolicy;

public class VerifyPolicyPrefs {

    public static <P extends IVerifyPolicy<C>, C extends IVerifyContext> P forEntityType(
            Class<? extends IVerifiable<C>> entityType) {
        return null;
    }

}
