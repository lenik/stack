package com.bee32.plover.model.qualifier;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PriorityTest {

    public static final Priority high = new Priority("high", 1, 1);
    public static final Priority middle = new Priority("middle", 2, 0);
    public static final Priority center = new Priority("center", 2, 0);
    public static final Priority low = new Priority("low", 2, 3);

    @Test
    public void lookAndFeel() {
        assertEquals("high", high.toString());
    }

}
