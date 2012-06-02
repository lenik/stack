package com.bee32.plover.model.stage;

import java.lang.reflect.Method;

public class MethodElement
        extends StagedElement {

    final Method method;

    public MethodElement(Method method) {
        super(method.getName());
        this.method = method;
    }

}
