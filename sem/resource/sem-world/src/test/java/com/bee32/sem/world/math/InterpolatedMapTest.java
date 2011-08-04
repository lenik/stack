package com.bee32.sem.world.math;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InterpolatedMapTest
        extends Assert {

    static final double EPS = 1e-20;

    InterpolatedMap imap = new InterpolatedMap();

    @Before
    public void setup() {
        imap.put(10, 100.0);
        imap.put(20, 200.0);
        imap.put(30, 400.0);
    }

    @Test
    public void testGetInterpolatedNoPoint()
            throws Exception {
        InterpolatedMap imap = new InterpolatedMap();
        assertEquals(Double.NaN, imap.eval(10), EPS);
    }

    @Test
    public void testGetInterpolatedCenterPoint()
            throws Exception {
        assertEquals(150.0, imap.eval(15), EPS);
    }

    @Test
    public void testGetInterpolated()
            throws Exception {
        assertEquals(120.0, imap.eval(12), EPS);
    }

    @Test
    public void testGetInterpolatedNoA()
            throws Exception {
        assertEquals(100.0, imap.eval(5), EPS);
    }

    @Test
    public void testGetInterpolatedNoB()
            throws Exception {
        assertEquals(400.0, imap.eval(55), EPS);
    }

    @Test
    public void testGetInterpolatedBoundaries()
            throws Exception {
        assertEquals(100.0, imap.eval(10), EPS);
        assertEquals(200.0, imap.eval(20), EPS);
        assertEquals(400.0, imap.eval(30), EPS);
    }

}
