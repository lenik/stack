package com.bee32.sem.world.monetary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bee32.sems.codes.util.InterpolatedMap;

/**
 * Author: ztf
 * Create Time: 11-1-28 下午12:21
 */
public class InterpolatedMapTest extends Assert {

    InterpolatedMap imap = new InterpolatedMap();

    @Before
    public void setup() {
        imap.put(10, 100.0);
        imap.put(20, 200.0);
        imap.put(30, 400.0);
    }

    @Test
    public void testGetInterpolatedNoPoint() throws Exception {
        InterpolatedMap imap = new InterpolatedMap();
        assertEquals(Double.NaN, imap.getInterpolated(10), 0.000001);
    }

    @Test
    public void testGetInterpolatedCenterPoint() throws Exception {
        assertEquals(150.0, imap.getInterpolated(15), 0.000001);
    }

    @Test
    public void testGetInterpolated() throws Exception {
        assertEquals(120.0, imap.getInterpolated(12), 0.000001);
    }

    @Test
    public void testGetInterpolatedNoA() throws Exception {
        assertEquals(100.0, imap.getInterpolated(5), 0.000001);
    }

    @Test
    public void testGetInterpolatedNoB() throws Exception {
        assertEquals(400.0, imap.getInterpolated(55), 0.000001);
    }

    @Test
    public void testGetInterpolatedBoundaries() throws Exception {
        assertEquals(100.0, imap.getInterpolated(10), 0.000001);
        assertEquals(200.0, imap.getInterpolated(20), 0.000001);
        assertEquals(400.0, imap.getInterpolated(30), 0.000001);
    }

}
