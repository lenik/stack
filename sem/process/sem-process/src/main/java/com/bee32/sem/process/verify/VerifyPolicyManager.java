package com.bee32.sem.process.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.arch.util.ClassUtil;

public class VerifyPolicyManager {

    static Set<Class<? extends IVerifyPolicy<?>>> policyTypes;

    static {
        load();
    }

    @SuppressWarnings({ "unchecked" })
    static void load() {
        policyTypes = new HashSet<Class<? extends IVerifyPolicy<?>>>();

        try {
            for (Class<?> policyClass : ServicePrototypeLoader.load(IVerifyPolicy.class)) {
                policyTypes.add((Class<? extends IVerifyPolicy<?>>) policyClass);
            }
        } catch (Exception e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }
    }

    public static Collection<Class<? extends IVerifyPolicy<?>>> list() {
        return policyTypes;
    }

    public static Collection<Class<? extends IVerifyPolicy<?>>> forContext(
            Class<? extends IVerifyContext> providedContext) {
        List<Class<? extends IVerifyPolicy<?>>> matchedPolicyTypes = new ArrayList<Class<? extends IVerifyPolicy<?>>>();

        for (Class<? extends IVerifyPolicy<?>> policyType : policyTypes) {

            Class<? extends IVerifyContext> requiredContext = ClassUtil.infer1(//
                    policyType, VerifyPolicy.class, 0);

            if (requiredContext.isAssignableFrom(providedContext))
                matchedPolicyTypes.add(policyType);
        }

        return matchedPolicyTypes;
    }

    public static Collection<Class<? extends IVerifyPolicy<?>>> forBean(Class<? extends IVerifiable<?>> beanType) {
        Class<? extends IVerifyContext> contextClass = ClassUtil.infer1(beanType, IVerifiable.class, 0);
        return forContext(contextClass);
    }

    public static <P extends IVerifyPolicy<C>, C extends IVerifyContext> P forEntityType(
            Class<? extends IVerifiable<C>> entityType) {
        return null;
    }

}
