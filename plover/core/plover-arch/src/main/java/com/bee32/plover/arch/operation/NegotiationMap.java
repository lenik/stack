package com.bee32.plover.arch.operation;

import java.util.HashMap;
import java.util.Map;

import javax.free.INegotiation;
import javax.free.NegotiationParameter;

public class NegotiationMap
        implements IParameterMap {

    private final INegotiation negotiation;

    private Map<String, Object> map;

    public NegotiationMap(INegotiation negotiation) {
        if (negotiation == null)
            throw new NullPointerException("negotiation");
        this.negotiation = negotiation;
    }

    @Override
    public Object get(int parameterIndex) {
        return negotiation.getParameter(parameterIndex);
    }

    @Override
    public Object get(Object parameterKey) {
        if (map == null) {
            synchronized (this) {
                if (map == null) {
                    map = new HashMap<String, Object>();
                    for (NegotiationParameter param : negotiation) {
                        String id = param.getId();
                        Object val = param.getValue();
                        map.put(id, val);
                    }
                }
            }
        }
        return map.get(parameterKey);
    }

}
