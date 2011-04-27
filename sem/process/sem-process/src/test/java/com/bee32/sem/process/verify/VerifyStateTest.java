package com.bee32.sem.process.verify;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class VerifyStateTest
        extends Assert {

    static {
        Locale.setDefault(Locale.ROOT);
    }

    @Test
    public void testNls() {
        assertEquals("Invalid", VerifyState.INVALID.getDisplayName());
    }

}
