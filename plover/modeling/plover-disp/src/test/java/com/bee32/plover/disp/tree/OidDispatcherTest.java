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
        tree.get(1, 3, 4, 6, 8).set("somewhere");
    }

    @Test
    public void testFully()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();

        Object actual = od.dispatch(tree, new TokenQueue("0"));
        assertEquals("X", actual);

        actual = od.dispatch(tree, new TokenQueue("1/2"));
        assertEquals("AB", actual);

        actual = od.dispatch(tree, "1/3/4/6/8");
        assertEquals("somewhere", actual);
    }

    @Test(expected = DispatchException.class)
    public void testIncomplete()
            throws DispatchException {
        OidDispatcher od = new OidDispatcher();
        Object actual = od.dispatch(tree, "1/3/4");
        assertNull(actual);
    }

}
