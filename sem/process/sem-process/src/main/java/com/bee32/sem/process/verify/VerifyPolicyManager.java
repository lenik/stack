package com.bee32.sem.process.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.ClassLocal;
import javax.free.IllegalUsageException;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.sem.process.verify.service.VerifiableIntroPostProcessor;

public class VerifyPolicyManager {

    static Map<Class<? extends IVerifyPolicy>, VerifyPolicyMetadata> policyMetadataMap;

    static {
        load();
    }

    static void load() {
        policyMetadataMap = new HashMap<Class<? extends IVerifyPolicy>, VerifyPolicyMetadata>();

        try {
            for (Class<? extends IVerifyPolicy> policyClass : ServicePrototypeLoader.load(IVerifyPolicy.class, false)) {
                // Ignore non-entity policy types.F
                if (VerifyPolicy.class.isAssignableFrom(policyClass)) {
                    VerifyPolicyMetadata metadata = new VerifyPolicyMetadata(policyClass);
                    policyMetadataMap.put(policyClass, metadata);
                }
            }
        } catch (Exception e) {
            throw new IllegalUsageException(e.getMessage(), e);
        }
    }

    public static VerifyPolicyMetadata getMetadata(Class<? extends IVerifyPolicy> policyType) {
        return policyMetadataMap.get(policyType);
    }

    public static Collection<Class<? extends IVerifyPolicy>> list() {
        return policyMetadataMap.keySet();
    }

    public static Collection<Class<? extends IVerifyPolicy>> forContext(Class<? extends IVerifyContext> providedContext) {
        List<Class<? extends IVerifyPolicy>> matches = new ArrayList<Class<? extends IVerifyPolicy>>();

        for (VerifyPolicyMetadata policyMetadata : policyMetadataMap.values()) {

            Class<? extends IVerifyContext> contextClass = policyMetadata.getContextClass();

            if (contextClass.isAssignableFrom(providedContext))
                matches.add(policyMetadata.getPolicyType());
        }

        return matches;
    }

    public static Collection<Class<? extends IVerifyPolicy>> forBean(Class<? extends IVerifiable<?>> beanType) {
        Class<? extends IVerifyContext> contextClass = ClassUtil.infer1(beanType, IVerifiable.class, 0);
        return forContext(contextClass);
    }

    static final ClassLocal<Collection<Class<? extends IVerifyPolicy>>> candidateMap;
    static {
        candidateMap = new ClassLocal<Collection<Class<? extends IVerifyPolicy>>>();
    }

    public static synchronized void addVerifiableType(Class<?> entityClass) {
        Class<? extends IVerifyContext> entityContextClass = ClassUtil.infer1(entityClass, IVerifiable.class, 0);

        Collection<Class<? extends IVerifyPolicy>> candidates = forContext(entityContextClass);

        candidateMap.put(entityClass, candidates);
    }

    /**
     * @see VerifiableIntroPostProcessor
     */
    public static Collection<Class<?>> getVerifiableTypes() {
        return candidateMap.keySet();
    }

    public static Collection<Class<? extends IVerifyPolicy>> getCandidates(Class<?> verifiableType) {
        if (!candidateMap.containsKey(verifiableType))
            throw new IllegalUsageException("Verify-Policy is not suitable for this type: " + verifiableType);
        else
            return candidateMap.get(verifiableType);
    }

}
