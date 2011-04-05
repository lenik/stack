package com.bee32.plover.disp.dyna;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.DispatchModule;
import com.bee32.plover.disp.IDispatcher;
import com.bee32.plover.disp.type.FieldDispatcher;
import com.bee32.plover.disp.util.Arrival;
import com.bee32.plover.disp.util.IArrival;

public class DynamicDispatcherTest
        extends DispatchModule {

    public String field1 = "hello";

    @Override
    public IDispatcher getDispatcher() {
        return new FieldDispatcher();
    }

    @Test
    public void testIndirectFieldDispatch()
            throws DispatchException {
        IArrival context = new Arrival(this);

        DynamicDispatcher dd = new DynamicDispatcher();

        context = dd.dispatch(context, "field1");
        Object result = context.getTarget();

        Assert.assertSame(field1, result);
    }

}
