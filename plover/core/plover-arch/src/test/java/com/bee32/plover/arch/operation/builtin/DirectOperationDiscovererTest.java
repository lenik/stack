package com.bee32.plover.arch.operation.builtin;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.operation.IOperation;
import com.bee32.plover.arch.operation.IOperationDiscoverer;
import com.bee32.plover.arch.operation.OperationContext;
import com.bee32.plover.arch.operation.OperationDiscovererManager;
import com.bee32.plover.arch.operation.OperationFusion;

public class DirectOperationDiscovererTest
        extends Assert {

    @Test
    public void testProvider() {
        for (IOperationDiscoverer discoverer : OperationDiscovererManager.getDiscoverers())
            if (discoverer instanceof DirectOperationDiscoverer)
                return;
        fail("Not registered");
    }

    @Test
    public void testHello()
            throws Exception {
        HelloBean foo = new HelloBean();

        OperationFusion fusion = OperationFusion.getInstance();
        IOperation worldOp = fusion.getOperation(foo, "world");

        OperationContext context = new OperationContext();
        Object result = worldOp.execute(foo, context);
        assertEquals("The World", result);
    }

    @Test
    public void testTypeContext()
            throws Exception {
        HelloBean foo = new HelloBean();

        OperationFusion fusion = OperationFusion.getInstance();
        IOperation saveVar = fusion.getOperation(foo, "saveVar");

        String str = "abc";
        Long num = 123L;

        OperationContext context = new OperationContext();
        context.registerContext(str);
        context.registerContext(num);

        saveVar.execute(foo, context);

        assertNotNull(foo.getLastString());
        assertNotNull(foo.getLastLong());
    }

}
