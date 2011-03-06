package com.bee32.plover.arch.operation.builtin;

import java.util.Map;

import javax.free.FinalNegotiation;
import javax.free.Mandatory;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.IndexedContext;
import com.bee32.plover.arch.operation.NegotiationContext;
import com.bee32.plover.arch.operation.OperationContext;

public class MethodOperationDiscovererTest
        extends Assert {

    MethodOperationDiscoverer discoverer = new MethodOperationDiscoverer();

    static OperationContext v(Object... params) {
        return new OperationContext(params);
    }

    @Test
    public void executeByVector()
            throws Exception {
        HelloBean foo = new HelloBean();
        Map<String, IOperation> operations = discoverer.getOperations(foo);
        IOperation operation = operations.get("hello");
        Object result = operation.execute(foo, new IndexedContext("cat", 13));
        assertEquals("cat/13", result);
    }

    @Test
    @Ignore
    public void executeByParamIndex()
            throws Exception {
        HelloBean foo = new HelloBean();
        Map<String, IOperation> operations = discoverer.getOperations(foo);
        IOperation operation = operations.get("hello");

        FinalNegotiation n = new FinalNegotiation( //
                new Mandatory("0", "cat"), //
                new Mandatory("1", 13));

        Object result = operation.execute(foo, new NegotiationContext(n));
        assertEquals("cat/13", result);
    }

    @Test
    @Ignore
    public void executeByParamMixed()
            throws Exception {
        HelloBean foo = new HelloBean();
        Map<String, IOperation> operations = discoverer.getOperations(foo);
        IOperation operation = operations.get("hello");

        FinalNegotiation n = new FinalNegotiation( //
                new Mandatory("cat"), //
                new Mandatory("1", 13));

        Object result = operation.execute(foo, new NegotiationContext(n));
        assertEquals("cat/13", result);
    }

}
