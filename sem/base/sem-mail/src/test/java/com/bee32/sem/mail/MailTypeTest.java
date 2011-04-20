package com.bee32.sem.mail;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

public class MailTypeTest {

    static {
        Locale.setDefault(Locale.ROOT);
    }

    @Test
    public void testNls() {
        String bcastName = MailType.BCAST.getDisplayName();
        assertEquals("Broadcast Message", bcastName);
    }

}
