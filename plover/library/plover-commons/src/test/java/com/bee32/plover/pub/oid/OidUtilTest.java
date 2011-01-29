package com.bee32.plover.pub.oid;

import org.junit.Assert;
import org.junit.Test;

public class OidUtilTest
        extends Assert {

    @Oid({ 10, 3 })
    class A {
    }

    class B {
    }

    @Test
    public void testGetOid() {
        OidVector v10_3 = new OidVector(10, 3);
        OidVector a = OidUtil.getOid(A.class);
        OidVector b = OidUtil.getOid(B.class);
        assertEquals(v10_3, a);
        assertNull(b);
    }

    @Test
    public void testFormat() {
        OidVector oid = new OidVector(3, 1, 2);
        assertEquals("3.1.2", oid.format('.'));
        assertEquals("", new OidVector().format('/'));
    }

    @Test
    public void testParse() {
        OidVector v312 = new OidVector(3, 1, 2);
        assertEquals(v312, OidUtil.parse('.', "3.1.2"));
        assertEquals(v312, OidUtil.parse('/', "3/1/2"));
    }

}
