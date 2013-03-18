package com.bee32.plover.model.qualifier;

import static com.bee32.plover.model.qualifier.PriorityTest.*;

import org.junit.Assert;
import org.junit.Test;

public class QualifierComparatorTest
        extends Assert {

    QualifierComparator c = QualifierComparator.getInstance();

    @Test
    public void testRegular() {
        int cmp = c.compare(high, middle);
        assertTrue(cmp < 0);
        cmp = c.compare(low, high);
        assertTrue(cmp > 0);
    }

    @Test
    public void testSame() {
        Priority high1 = new Priority("high", 1, 1);
        int cmp = c.compare(high, high1);
        assertEquals(0, cmp);
    }

    @Test
    public void testOnlyNameDiff() {
        int cmp = c.compare(middle, center);
        assertTrue(cmp > 0);
        cmp = c.compare(center, middle);
        assertTrue(cmp < 0);
    }

}
