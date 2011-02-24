package com.bee32.plover.disp.tree;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.disp.DispatchException;
import com.bee32.plover.disp.util.TokenQueue;
import com.bee32.plover.pub.oid.OidTree;

public class OidDispatcherTest
        extends Assert {

    OidTree<String> tree;

    public OidDispatcherTest() {
        tree = new OidTree<String>();
        tree.get(0).set("X");
        tree.get(1, 2).set("AB");
        tree.get(1, 2, 3).set("ABC");
        tree.get(1, 3, 4, 6, 8).set("somewhere");
    }

    @Test
    public void testFully()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();
        TokenQueue tq;

        Object actual = od.dispatch(tree, tq = new TokenQueue("0"));
        assertEquals("X", actual);
        assertEquals(0, tq.available());

        actual = od.dispatch(tree, "1/3/4/6/8");
        assertEquals("somewhere", actual);
    }

    @Test
    public void testOverlapped()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();
        TokenQueue tq;

        Object actual = od.dispatch(tree, tq = new TokenQueue("1/2"));
        assertEquals("AB", actual);
        assertEquals(0, tq.available());

        actual = od.dispatch(tree, tq = new TokenQueue("1/2/3"));
        assertEquals("ABC", actual);
        assertEquals(0, tq.available());
    }

    @Test
    public void testIncomplete()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();
        TokenQueue tq;

        Object actual = od.dispatch(tree, tq = new TokenQueue("1/3/4"));
        assertNull(actual);
        assertEquals(3, tq.available());
    }

    @Test
    public void testIncompleteOverlapped()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();
        TokenQueue tq;

        Object actual = od.dispatch(tree, tq = new TokenQueue("1/2/4"));
        assertEquals("AB", actual);
        assertEquals(1, tq.available());
    }

    @Test(expected = DispatchException.class)
    public void testIncompleteForce()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();
        od.dispatch(tree, ("1/3/4"));
    }

}
