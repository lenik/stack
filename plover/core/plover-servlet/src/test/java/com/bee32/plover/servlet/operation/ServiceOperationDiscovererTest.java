package com.bee32.plover.servlet.operation;

import javax.free.FinalNegotiation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.IOperationDiscoverer;
import com.bee32.plover.arch.operation.OperationDiscovererManager;
import com.bee32.plover.arch.operation.OperationFusion;

public class ServiceOperationDiscovererTest
        extends Assert {

    @Test
    public void testProvider() {
        for (IOperationDiscoverer discoverer : OperationDiscovererManager.getDiscoverers())
            if (discoverer instanceof ServiceOperationDiscoverer)
                return;
        fail("Not registered");
    }

    @Test
    public void testHello()
            throws Exception {
        FooController foo = new FooController();

        OperationFusion fusion = OperationFusion.getInstance();
        IOperation helloOp = fusion.getOperation(foo, "hello");

        FinalNegotiation n = new FinalNegotiation();
        Object result = helloOp.execute(foo, n);
        assertEquals("world", result);
    }

    @Test
    public void testIndexedParam()
            throws Exception {
        FooController foo = new FooController();

        OperationFusion fusion = OperationFusion.getInstance();
        IOperation mockTestOp = fusion.getOperation(foo, "testmock");

        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse resp = new MockHttpServletResponse();
        // FinalNegotiation n = new FinalNegotiation(req, resp);
        mockTestOp.execute(foo, req, resp);

        assertNotNull(foo.getLastRequest());
        assertNotNull(foo.getLastResponse());
    }

}
