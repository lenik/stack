package com.bee32.sem.process.verify;

public class ContextClassUtil {

    public static Class<?> getContextClass(Class<?> verifyPolicyClass) {
        ContextClass contextClassAnn = verifyPolicyClass.getAnnotation(ContextClass.class);

        if (contextClassAnn == null)
            return Object.class;

        else
            return contextClassAnn.value();
    }

}
