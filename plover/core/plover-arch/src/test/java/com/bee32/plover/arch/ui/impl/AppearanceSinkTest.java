package com.bee32.plover.arch.ui.impl;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.bee32.plover.arch.ui.res.InjectedAppearance;
import com.bee32.plover.arch.util.res.LoopbackDispatcher;

public class AppearanceSinkTest {

    InjectedAppearance sink;

    public AppearanceSinkTest() {
        Locale.setDefault(new Locale("not-exist-locale"));

        sink = new InjectedAppearance(AppearanceSinkTest.class);
        LoopbackDispatcher dispatcher = new LoopbackDispatcher(sink);
        dispatcher.dispatchClassResource(getClass(), Locale.ENGLISH);
    }

    @Test
    public void testBasicProperties()
            throws Exception {
        assertEquals("Display Name", sink.getDisplayName());
        assertEquals("A descriptive string.", sink.getDescription());
    }

}
