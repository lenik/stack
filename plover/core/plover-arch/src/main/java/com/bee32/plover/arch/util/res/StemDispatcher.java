package com.bee32.plover.arch.util.res;

public class StemDispatcher
        extends PropertyDispatcher {

    private final IPropertyAcceptor sink;

    public StemDispatcher(IPropertyAcceptor sink) {
        if (sink == null)
            throw new NullPointerException("sink");
        this.sink = sink;
    }

    @Override
    protected void dispatch(String key, String content) {
        sink.receive(key, content);
    }

}
