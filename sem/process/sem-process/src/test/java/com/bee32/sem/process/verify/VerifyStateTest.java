package com.bee32.sem.process.verify;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import com.bee32.sem.event.EventState;

public class VerifyStateTest
        extends Assert {

    static {
        Locale.setDefault(Locale.ROOT);
    }

    @Test
    public void testNls() {
        assertEquals("Invalid", VerifyState.INVALID.getDisplayName());
    }

    @Test
    public void testEnumInherits() {
        VerifyState verifyState = VerifyState.PENDING;
        EventState<?> eventState = EventState.valueOf(verifyState.getValue());
        assertEquals(eventState, verifyState);
    }

    // @Test
    public void testDumpInherits() {
        for (EventState<?> st : EventState.values())
            System.out.println(st.getName());
    }

}
