package com.bee32.plover.arch.i18n.nls;

public abstract class PrefixDispatcher
        extends PropertyDispatcher {

    public abstract void registerSink(String prefix, IPropertySink sink);

}
