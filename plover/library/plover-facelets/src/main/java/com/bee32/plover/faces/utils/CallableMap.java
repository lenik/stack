package com.bee32.plover.faces.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Callable-map constructs two kind of verb usage: push and invoke.
 *
 * <pre>
 * method-call: push-verb* invoke-verb
 * push-verb: push(method-param)
 * invoke-verb: invoke()
 * </pre>
 */
public abstract class CallableMap
        extends VerbMap {

    private final List<Object> parameterStack;

    public CallableMap() {
        parameterStack = new ArrayList<Object>();
    }

    @Override
    protected int getRequiredArguments(Object verb) {
        if ("push".equals(verb))
            return 1;
        if ("invoke".equals(verb))
            return 1;
        return 0;
    }

    @Override
    protected Object execute(Object verb) {
        if ("push".equals(verb)) {
            Object arg = getArgument(0);
            parameterStack.add(arg);
            return this;
        }

        String methodName;
        if ("invoke".equals(verb)) {
            Object arg = getArgument(0);
            methodName = String.valueOf(arg);
        } else
            methodName = String.valueOf(verb);

        return invoke(methodName, parameterStack.toArray(new Object[0]));
    }

    protected abstract Object invoke(String methodName, Object... args);

}
