package com.bee32.plover.arch.ui.impl;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.bee32.plover.arch.i18n.nls.DefaultDispatcher;
import com.bee32.plover.arch.ui.impl.AppearanceSink;

public class AppearanceSinkTest {

    AppearanceSink sink;

    public AppearanceSinkTest() {
        Locale.setDefault(new Locale("not-exist-locale"));

        sink = new AppearanceSink(AppearanceSinkTest.class);
        DefaultDispatcher dispatcher = new DefaultDispatcher(sink);
        dispatcher.visitClassResource(getClass(), Locale.ENGLISH);
    }

    @Test
    public void testBasicProperties()
            throws Exception {
        assertEquals("Display Name", sink.getDisplayName());
        assertEquals("A descriptive string.", sink.getDescription());
    }

}
