package com.bee32.plover.arch.ui.res;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.bee32.plover.arch.util.res.StemDispatcher;

public class InjectedAppearanceTest {

    InjectedAppearance appearance;

    public InjectedAppearanceTest() {
        Locale.setDefault(new Locale("not-exist-locale"));

        appearance = new InjectedAppearance(InjectedAppearanceTest.class);
        StemDispatcher dispatcher = new StemDispatcher(appearance);
        dispatcher.dispatchClassResource(getClass(), Locale.ENGLISH);
    }

    @Test
    public void testBasicProperties()
            throws Exception {
        assertEquals("Display Name", appearance.getDisplayName());
        assertEquals("A descriptive string.", appearance.getDescription());
    }

}
