package com.bee32.plover.arch.util.res;

public abstract class PrefixDispatchStrategy
        extends PropertyDispatchStrategy {

    public abstract void registerPrefix(String prefix, IPropertyAcceptor propertyAcceptor);

}
