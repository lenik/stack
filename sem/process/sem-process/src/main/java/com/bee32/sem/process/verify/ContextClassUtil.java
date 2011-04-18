package com.bee32.sem.process.verify;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.util.ClassUtil;

public class ContextClassUtil {

    public static Class<? extends IVerifyContext> getContextClass(Class<?> verifyPolicyClass) {
        ContextClass contextClassAnn = verifyPolicyClass.getAnnotation(ContextClass.class);

        if (contextClassAnn != null)
            return contextClassAnn.value();

        Class<?>[] pv = ClassUtil.getOriginPVClass(verifyPolicyClass);
        Class<?> tv = pv[0];

        if (IVerifyContext.class.isAssignableFrom(tv))
            return (Class<? extends IVerifyContext>) IVerifyContext.class.asSubclass(tv);

        throw new IllegalUsageException("Context class is unknown for " + verifyPolicyClass);
    }

}
