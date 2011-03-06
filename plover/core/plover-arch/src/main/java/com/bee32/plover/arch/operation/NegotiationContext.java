package com.bee32.plover.arch.operation;

import javax.free.INegotiation;
import javax.free.NegotiationParameter;

public class NegotiationContext
        extends OperationContext {

    private static final long serialVersionUID = 1L;

    private final INegotiation negotiation;

    public NegotiationContext(INegotiation negotiation) {
        if (negotiation == null)
            throw new NullPointerException("negotiation");
        this.negotiation = negotiation;

        for (NegotiationParameter param : negotiation) {
            String id = param.getId();
            Object val = param.getValue();
            put(id, val);
        }
    }

    @Override
    public Object get(int parameterIndex) {
        return negotiation.getParameter(parameterIndex);
    }

}
