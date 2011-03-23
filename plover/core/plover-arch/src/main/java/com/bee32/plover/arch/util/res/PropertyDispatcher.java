package com.bee32.plover.arch.util.res;

import javax.free.StringPart;

public abstract class PropertyDispatcher
        implements IPropertyDispatcher {

    protected final PropertyDispatchStrategy strategy;

    private boolean required;

    public PropertyDispatcher(PropertyDispatchStrategy strategy) {
        if (strategy == null)
            throw new NullPointerException("strategy");
        this.strategy = strategy;
    }

    public PropertyDispatchStrategy getStrategy() {
        return strategy;
    }

    @Override
    public void require() {
        if (!required) {
            synchronized (this) {
                if (!required) {
                    dispatch();
                }
            }
        }
    }

    @Override
    public String toString() {
        String dispatcherType = getClass().getSimpleName();

        if (dispatcherType.contains("$"))
            dispatcherType = StringPart.afterLast(dispatcherType, '$');

        String strategyType = strategy.getClass().getSimpleName();

        return dispatcherType + "/" + strategyType;
    }

}
