package com.bee32.plover.arch.util.res;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections15.iterators.SingletonIterator;

public class StemDispatchStrategy
        extends PropertyDispatchStrategy {

    private final IPropertyAcceptor sink;

    public StemDispatchStrategy(IPropertyAcceptor sink) {
        if (sink == null)
            throw new NullPointerException("sink");
        this.sink = sink;
    }

    @Override
    public Iterable<IPropertyAcceptor> getAcceptors() {
        return new Iterable<IPropertyAcceptor>() {

            @Override
            public Iterator<IPropertyAcceptor> iterator() {
                return new SingletonIterator<IPropertyAcceptor>(sink);
            }

        };
    }

    @Override
    public Map<String, IPropertyAcceptor> getAcceptorMap() {
        return null;
    }

    @Override
    public void dispatch(String key, String content) {
        sink.receive(key, content);
    }

}
