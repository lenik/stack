package com.bee32.plover.arch.operation;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

public class OperationUtil {

    public static String getAnnotatedOperationName(AnnotatedElement annotatedElement) {
        Operation operationAnnotation = annotatedElement.getAnnotation(Operation.class);
        if (operationAnnotation == null)
            return null;
        String name = operationAnnotation.value();
        return name;
    }

    public static <M extends AnnotatedElement & Member> String getOperationName(M member) {
        String name = getAnnotatedOperationName(member);

        if (name == null)
            return null;

        if (name.isEmpty())
            name = member.getName();

        return name;
    }

}
