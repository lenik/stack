package com.bee32.sem.process.verify;

import java.lang.reflect.Type;

import com.bee32.plover.arch.util.ClassUtil;

public class ContextClassUtil {

    public static Class<? extends IVerifyContext> getContextClass(Class<?> verifyPolicyClass) {
        ContextClass contextClassAnn = verifyPolicyClass.getAnnotation(ContextClass.class);

        if (contextClassAnn != null)
            return contextClassAnn.value();

        Type[] verifyPolicyArgs = ClassUtil.getTypeArgs(verifyPolicyClass, VerifyPolicy.class);

        return ClassUtil.bound1(verifyPolicyArgs[0]);
    }

}
