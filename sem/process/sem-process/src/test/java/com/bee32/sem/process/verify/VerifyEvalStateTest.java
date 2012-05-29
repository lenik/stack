package com.bee32.sem.process.verify;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.event.EventState;

public class VerifyEvalStateTest
        extends Assert {

    static {
        Locale.setDefault(Locale.ROOT);
    }

    @Test
    public void testNls() {
        assertEquals("Invalid", VerifyEvalState.INVALID.getDisplayName());
    }

    @Test
    public void testEnumInherits() {
        VerifyEvalState verifyState = VerifyEvalState.PENDING;
        EventState<?> eventState = EventState.forValue(verifyState.getValue());
        assertEquals(eventState, verifyState);
    }

    // @Test
    public void testDumpInherits() {
        for (EventState<?> st : EventState.values())
            System.out.println(st.getName());
    }

    @Test
    public void testUnknownValue() {
        Integer unknownValue = VerifyEvalState.UNKNOWN.getValue();
        // System.out.println(unknownValue);
        assertEquals((Integer) 0x02000001, unknownValue);
    }

}
