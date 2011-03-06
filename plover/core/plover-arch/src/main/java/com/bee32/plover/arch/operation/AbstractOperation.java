package com.bee32.plover.arch.operation;

import java.util.Map;

import javax.free.INegotiation;

import com.bee32.plover.arch.Component;

public abstract class AbstractOperation
        extends Component
        implements IOperation {

    public AbstractOperation() {
        super();
    }

    public AbstractOperation(String name) {
        super(name);
    }

    @Override
    public Object execute(Object instance, Object... parameters)
            throws Exception {
        IParameterMap pm = new IndexedParameterMap(parameters);
        return execute(instance, pm);
    }

    @Override
    public Object execute(Object instance, INegotiation negotiation)
            throws Exception {
        NegotiationMap map = new NegotiationMap(negotiation);
        return execute(instance, map);
    }

    @Override
    public Object execute(Object instance, Map<Object, Object> parameters)
            throws Exception {
        IParameterMap pm = new MapParameterMap(parameters);
        return execute(instance, pm);
    }

}
