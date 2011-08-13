package com.bee32.sem.world.color;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

public class TrueColorTest
        extends Assert {

    @Test
    public void testAwt() {
        TrueColor tc = new TrueColor(Color.PINK);
        System.out.println(tc);
    }

}
