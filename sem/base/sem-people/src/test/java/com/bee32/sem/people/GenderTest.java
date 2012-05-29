package com.bee32.sem.people;

import org.junit.Test;

import com.bee32.plover.arch.util.EnumAltTestCase;
import com.bee32.plover.arch.util.NoSuchEnumException;

public class GenderTest
        extends EnumAltTestCase {

    @Test
    public void testGetInstance() {
        Gender male = Gender.forValue('m');
        assertEquals(Gender.MALE, male);

        Gender female = Gender.forValue('f');
        assertEquals(Gender.FEMALE, female);
    }

    @Test(expected = NoSuchEnumException.class)
    public void testGetInstance_NotExist() {
        Gender.forValue('Z');
    }

    /**
     * TODO ContextLocalGroup
     */
    @Test
    public void testGetLabel() {
        String name = Gender.MALE.getDisplayName();
        assertEquals("Male", name);
    }

}
