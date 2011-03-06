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
        IOperationContext context = new IndexedContext(parameters);
        return execute(instance, context);
    }

    @Override
    public Object execute(Object instance, INegotiation negotiation)
            throws Exception {
        NegotiationContext context = new NegotiationContext(negotiation);
        return execute(instance, context);
    }

    @Override
    public Object execute(Object instance, Map<String, ?> parameters)
            throws Exception {
        IOperationContext context = new OperationContext();
        context.putAll(parameters); // CopyOnWriteTreeMap();
        return execute(instance, context);
    }

}
