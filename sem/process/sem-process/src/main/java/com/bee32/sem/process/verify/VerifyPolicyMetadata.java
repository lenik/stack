package com.bee32.sem.process.verify;

import javax.free.IllegalUsageException;

public class VerifyPolicyMetadata {

    final Class<? extends IVerifyPolicy> policyType;
    final Class<? extends IVerifyContext> contextClass;

    public VerifyPolicyMetadata(Class<? extends IVerifyPolicy> policyType) {
        this.policyType = policyType;

        ForVerifyContext _fvc = policyType.getAnnotation(ForVerifyContext.class);
        if (_fvc == null)
            throw new IllegalUsageException("Annotation " + ForVerifyContext.class + " is absent for " + policyType);
        contextClass = _fvc.value();
    }

    public Class<? extends IVerifyPolicy> getPolicyType() {
        return policyType;
    }

    public Class<? extends IVerifyContext> getContextClass() {
        return contextClass;
    }

    public boolean isUsefulFor(Class<?> actualContextClass) {
        return contextClass.isAssignableFrom(actualContextClass);
    }

}
