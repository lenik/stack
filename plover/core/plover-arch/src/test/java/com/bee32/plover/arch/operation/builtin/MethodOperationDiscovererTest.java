package com.bee32.plover.arch.operation.builtin;

import java.util.Map;

import javax.free.FinalNegotiation;
import javax.free.Mandatory;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.operation.IOperation;

public class MethodOperationDiscovererTest
        extends Assert {

    MethodOperationDiscoverer discoverer = new MethodOperationDiscoverer();

    @Test
    public void executeByVector()
            throws Exception {
        FooOperations foo = new FooOperations();
        Map<String, IOperation> operations = discoverer.getOperations(foo);
        IOperation operation = operations.get("hello");
        Object result = operation.execute(foo, "cat", 13);
        assertEquals("cat/13", result);
    }

    @Test
    public void executeByParamIndex()
            throws Exception {
        FooOperations foo = new FooOperations();
        Map<String, IOperation> operations = discoverer.getOperations(foo);
        IOperation operation = operations.get("hello");

        FinalNegotiation n = new FinalNegotiation( //
                new Mandatory("0", "cat"), //
                new Mandatory("1", 13));

        Object result = operation.execute(foo, n);
        assertEquals("cat/13", result);
    }

    @Test
    public void executeByParamMixed()
            throws Exception {
        FooOperations foo = new FooOperations();
        Map<String, IOperation> operations = discoverer.getOperations(foo);
        IOperation operation = operations.get("hello");

        FinalNegotiation n = new FinalNegotiation( //
                new Mandatory("cat"), //
                new Mandatory("1", 13));

        Object result = operation.execute(foo, n);
        assertEquals("cat/13", result);
    }

}
