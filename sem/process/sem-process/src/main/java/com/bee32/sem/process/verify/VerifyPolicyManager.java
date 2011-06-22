package com.bee32.sem.process.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.sem.process.verify.service.VerifiableIntroPostProcessor;

public class VerifyPolicyManager {

    static Set<Class<? extends VerifyPolicy>> policyTypes;

    static {
        load();
    }

    static void load() {
        policyTypes = new HashSet<Class<? extends VerifyPolicy>>();

        try {
            for (Class<? extends VerifyPolicy> policyClass : ServicePrototypeLoader.load(VerifyPolicy.class)) {
                // Ignore non-entity policy types.F
                if (VerifyPolicy.class.isAssignableFrom(policyClass))
                    policyTypes.add(policyClass);
            }
        } catch (Exception e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }
    }

    public static Collection<Class<? extends VerifyPolicy>> list() {
        return policyTypes;
    }

    public static Collection<Class<? extends VerifyPolicy>> forContext(Class<? extends IVerifyContext> providedContext) {
        List<Class<? extends VerifyPolicy>> matchedPolicyTypes = new ArrayList<Class<? extends VerifyPolicy>>();

        for (Class<? extends VerifyPolicy> policyType : policyTypes) {

            Class<? extends IVerifyContext> requiredContext = ClassUtil.infer1(//
                    policyType, VerifyPolicy.class, 0);

            if (requiredContext.isAssignableFrom(providedContext))
                matchedPolicyTypes.add(policyType);
        }

        return matchedPolicyTypes;
    }

    public static Collection<Class<? extends VerifyPolicy>> forBean(Class<? extends IVerifiable<?>> beanType) {
        Class<? extends IVerifyContext> contextClass = ClassUtil.infer1(beanType, IVerifiable.class, 0);
        return forContext(contextClass);
    }

    static final ClassLocal<Collection<Class<? extends VerifyPolicy>>> candidateMap;
    static {
        candidateMap = new ClassLocal<Collection<Class<? extends VerifyPolicy>>>();
    }

    public static synchronized void addVerifiableType(Class<?> entityClass) {
        Class<? extends IVerifyContext> contextClass = ClassUtil.infer1(entityClass, IVerifiable.class, 0);

        Collection<Class<? extends VerifyPolicy>> candidates = forContext(contextClass);

        candidateMap.put(entityClass, candidates);
    }

    /**
     * @see VerifiableIntroPostProcessor
     */
    public static Collection<Class<?>> getVerifiableTypes() {
        return candidateMap.keySet();
    }

    public static Collection<Class<? extends VerifyPolicy>> getCandidates(Class<?> verifiableType) {
        return candidateMap.get(verifiableType);
    }

}
