package com.bee32.plover.arch.i18n.nls;

public class DefaultDispatcher
        extends PropertyDispatcher {

    private final IPropertySink sink;

    public DefaultDispatcher(IPropertySink sink) {
        if (sink == null)
            throw new NullPointerException("sink");
        this.sink = sink;
    }

    @Override
    protected void dispatch(String key, String content) {
        sink.receive(key, content);
    }

}
