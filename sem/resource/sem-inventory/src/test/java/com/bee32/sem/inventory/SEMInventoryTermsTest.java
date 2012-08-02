package com.bee32.sem.inventory;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.term.TermMessageInterpolator;

public class SEMInventoryTermsTest
        extends Assert {

    TermMessageInterpolator interpolator = TermMessageInterpolator.getInstance();

    @Test
    public void testBatch1Default() {
        String src = "Hello, ${tr.inventory.batch1}";
        String result = interpolator.process(src);
        assertEquals("Hello, Batch 1", result);
    }

}
