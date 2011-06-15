package com.bee32.sem.people;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.plover.arch.util.NoSuchEnumException;

public class GenderTest
        extends Assert {

    @Test
    public void testGetInstance() {
        Gender male = Gender.valueOf('m');
        assertEquals(Gender.MALE, male);

        Gender female = Gender.valueOf('f');
        assertEquals(Gender.FEMALE, female);
    }

    @Test(expected = NoSuchEnumException.class)
    public void testGetInstance_NotExist() {
        Gender.valueOf("x");
    }

    /**
     * TODO CLG
     */
    @Test
    public void testGetLabel() {
        String name = Gender.MALE.getDisplayName();
        assertEquals("Male", name);
    }

}
