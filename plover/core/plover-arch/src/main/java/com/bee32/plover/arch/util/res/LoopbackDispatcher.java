package com.bee32.plover.arch.util.res;

public class LoopbackDispatcher
        extends PropertyDispatcher {

    private final IPropertyAcceptor sink;

    public LoopbackDispatcher(IPropertyAcceptor sink) {
        if (sink == null)
            throw new NullPointerException("sink");
        this.sink = sink;
    }

    @Override
    protected void dispatch(String key, String content) {
        sink.receive(key, content);
    }

}
