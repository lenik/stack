package com.bee32.plover.disp.type;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;

public class FieldDispatcherTest
        extends Assert {

    public String publicField = "a";

    protected String protectedField = "b";

// @SuppressWarnings("unused")
    private String privateField = "c";

    @Test
    public void testDispatchPublicField()
            throws DispatchException {
        Object context = new FieldDispatcherTest();
        FieldDispatcher fd = new FieldDispatcher();
        Object target = fd.dispatch(context, "publicField");
        assertSame(publicField, target);
    }

    @Test(expected = DispatchException.class)
    public void testDispatchProtectedField()
            throws DispatchException {
        Object context = new FieldDispatcherTest();
        FieldDispatcher fd = new FieldDispatcher();
        Object target = fd.dispatch(context, "protectedField");
        assertSame(protectedField, target);
    }

    @Test(expected = DispatchException.class)
    public void testDispatchPrivateField()
            throws DispatchException {
        Object context = new FieldDispatcherTest();
        FieldDispatcher fd = new FieldDispatcher();
        Object target = fd.dispatch(context, "privateField");
        assertSame(privateField, target);
    }

}
