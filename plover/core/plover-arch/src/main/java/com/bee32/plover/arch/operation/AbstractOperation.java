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

    public Object execute(Object instance, Object... parameters)
            throws Exception {
        IOperationContext pm = new IndexedContext(parameters);
        return execute(instance, pm);
    }

    public Object execute(Object instance, INegotiation negotiation)
            throws Exception {
        NegotiationContext map = new NegotiationContext(negotiation);
        return execute(instance, map);
    }

    public Object execute(Object instance, Map<String, ?> parameters)
            throws Exception {
        IOperationContext pm = new OperationContext();
        pm.putAll(parameters); // CopyOnWriteTreeMap();
        return execute(instance, pm);
    }

}
