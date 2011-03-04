package com.bee32.plover.arch.operation;

import javax.free.INegotiation;

public interface IOperation {

    String getName();

    Object execute(Object instance, Object... parameters)
            throws Exception;

    Object execute(Object instance, INegotiation negotiation)
            throws Exception;

}
