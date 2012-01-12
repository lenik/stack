package com.bee32.plover.site.cfg;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class PrimefacesThemeTest
        extends Assert {

    @Test
    public void testNlsLabel() {
        Locale.setDefault(Locale.ROOT);
        String label = PrimefacesTheme.bluesky.getEntryLabel();
        assertEquals("Blue Sky", label);
    }

}
