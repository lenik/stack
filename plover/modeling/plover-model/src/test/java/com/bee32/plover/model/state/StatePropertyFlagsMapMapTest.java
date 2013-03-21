package com.bee32.plover.model.state;

import java.beans.IntrospectionException;

import org.junit.Assert;
import org.junit.Test;

public class StatePropertyFlagsMapMapTest
        extends Assert {

    @LockedStates({ 1, 4 })
    public String getColor() {
        return "red";
    }

    @Test
    public void testAnnotatedLockingState()
            throws IntrospectionException {
        StatePropertyFlagsMapMap pfmm = StatePropertyFlagsMapMap.forClass(getClass());

        PropetyFlagsMap pfm1 = pfmm.getPropertyFlagsMap(1);
        PropetyFlagsMap pfm2 = pfmm.getPropertyFlagsMap(2);
        PropetyFlagsMap pfm4 = pfmm.getPropertyFlagsMap(4);

        PropertyFlags color1 = pfm1.get("color");
        assertTrue(color1.isLocked());

        PropertyFlags nonExist = pfm1.get("nonExist");
        // assertNull(nonExist);
        assertFalse(nonExist.isLocked());

        PropertyFlags color2 = pfm2.get("color");
        assertFalse(color2.isLocked());

        PropertyFlags color4 = pfm4.get("color");
        assertTrue(color4.isLocked());
    }

}
