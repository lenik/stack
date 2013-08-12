package com.bee32.plover.arch.ui.res;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

/**
 * Display Name
 *
 * A descriptive string.
 *
 * @icon.hover Hover
 * @icon.hover.16x16 Hover-16x16
 * @icon.hover.32x32 Hover-32x32
 * @icon.hover.disabled HoverDisabled
 * @ref.spec.2009.tags refspec 2009
 * @ref.spec.2009.title Reference Specification 2009
 * @ref.spec.2009.url ../Spec2009.html
 * @ref.tutorial.tags tutorial simple
 * @ref.tutorial.title Simple Tutorial
 * @ref.tutorial.url http://www.example.com/tutorial-1.html
 */
public class InjectedAppearanceTest {

    InjectedAppearance appearance;

    @Test
    public void testBasicProperties()
            throws Exception {
        Locale.setDefault(new Locale("not-exist-locale"));

        appearance = new InjectedAppearance(InjectedAppearanceTest.class, Locale.ROOT);

        assertEquals("Display Name", appearance.getDisplayName());
        assertEquals("A descriptive string.", appearance.getDescription());
    }

}
