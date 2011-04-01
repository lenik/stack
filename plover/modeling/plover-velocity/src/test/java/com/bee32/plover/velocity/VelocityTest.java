package com.bee32.plover.velocity;

import org.junit.Assert;
import org.junit.Test;

public class VelocityTest
        extends Assert {

    static SimplePerson tom;
    static {
        tom = new SimplePerson("Tom", 17, false, "USA");
    }

    @Test
    public void testASL() {
        String result = Velocity.merge("ASL", tom);
        assertEquals("17/m/USA", result);
    }

}
