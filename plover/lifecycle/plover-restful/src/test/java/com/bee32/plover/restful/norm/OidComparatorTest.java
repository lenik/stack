package com.bee32.plover.restful.norm;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.restful.oid.OidComparator;
import com.bee32.plover.restful.oid.OidVector;

public class OidComparatorTest
        extends Assert {

    OidVector empty = new OidVector();
    OidVector a12 = new OidVector(1, 2);
    OidVector a123 = new OidVector(1, 2, 3);
    OidVector a124 = new OidVector(1, 2, 4);

    OidComparator cmp = OidComparator.getInstance();

    @Test
    public void testEmpty() {
        assertTrue(cmp.compare(empty, empty) == 0);
        assertTrue(cmp.compare(empty, a12) < 0);
        assertTrue(cmp.compare(a12, empty) > 0);
    }

    @Test
    public void testGoods() {
        assertTrue(cmp.compare(a12, a12) == 0);
        assertTrue(cmp.compare(a12, a123) < 0);
        assertTrue(cmp.compare(a123, a12) > 0);
    }

}
