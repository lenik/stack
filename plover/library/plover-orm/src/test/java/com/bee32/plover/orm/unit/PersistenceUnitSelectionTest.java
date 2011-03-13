package com.bee32.plover.orm.unit;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.Test;

public class PersistenceUnitSelectionTest {

    static int count = 0;

    @Test
    public void static1() {
        count++;
        System.out.println("Count=" + count);
    }

    @Test
    public void static2() {
        count++;
        System.out.println("Count=" + count);
    }

    @AfterClass
    public static void checkResult() {
        assertEquals(2, count);
    }

}
