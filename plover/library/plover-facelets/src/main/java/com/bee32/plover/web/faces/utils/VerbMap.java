package com.bee32.plover.web.faces.utils;

import java.util.ArrayList;
import java.util.List;

public abstract class VerbMap
        extends YesMap {

    private final List<Object> parameterStack;
    private Object verb;
    private transient int wants;

    public VerbMap() {
        this.parameterStack = new ArrayList<Object>();
    }

    @Override
    public synchronized Object get(Object key) {
        if (wants > 0) {
            Object parameter = key;
            parameterStack.add(parameter);

            if (--wants == 0) {
                Object ret = execute(verb);
                parameterStack.clear();
                return ret;
            }
        }

        assert parameterStack.isEmpty();

        verb = key;
        wants = getRequiredArguments(verb);
        if (wants == 0)
            return execute(verb);

        return this;
    }

    protected int getRequiredArguments(Object verb) {
        return 1;
    }

    protected int getArgumentCount() {
        return parameterStack.size();
    }

    protected Object getArgument(int index) {
        return parameterStack.get(index);
    }

    protected abstract Object execute(Object verb);

}
