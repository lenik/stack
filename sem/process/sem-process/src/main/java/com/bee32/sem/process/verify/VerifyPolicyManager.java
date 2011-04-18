package com.bee32.sem.process.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.service.ServicePrototypeLoader;

public class VerifyPolicyManager {

    static Set<Class<? extends IVerifyPolicy<?>>> verifyPoliicyClasses;

    static {
        load();
    }

    @SuppressWarnings("unchecked")
    static void load() {
        verifyPoliicyClasses = new HashSet<Class<? extends IVerifyPolicy<?>>>();

        Iterable<Class<? extends IVerifyPolicy<?>>> policyIterable;
        try {
            policyIterable = (Iterable<Class<? extends IVerifyPolicy<?>>>) (Object) ServicePrototypeLoader
                    .load(IVerifyPolicy.class);
        } catch (Exception e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }

        for (Class<? extends IVerifyPolicy<?>> policyClass : policyIterable) {
            verifyPoliicyClasses.add(policyClass);
        }
    }

    public static Collection<Class<? extends IVerifyPolicy<?>>> getAvailableVerifyPoliicyClasses() {
        return verifyPoliicyClasses;
    }

    public static Collection<Class<IVerifyPolicy<?>>> getAvailableVerifyPoliicyClasses(
            Class<? extends IVerifiable<IVerifyContext>> instanceClass) {
        List<Class<IVerifyPolicy<?>>> matchedPolicyClasses = new ArrayList<Class<IVerifyPolicy<?>>>();

        for (Class<? extends IVerifyPolicy<?>> policyClass : verifyPoliicyClasses) {
            Class<? extends IVerifyContext> contextClass = ContextClassUtil.getContextClass(policyClass);
            if (contextClass.isAssignableFrom(instanceClass))
                matchedPolicyClasses.add(policyClass);
        }

        return matchedPolicyClasses;
    }

}
