package com.bee32.plover.arch.util.res;

public abstract class PrefixDispatcher
        extends PropertyDispatcher {

    public abstract void registerPrefix(String prefix, IPropertyAcceptor sink);

}
