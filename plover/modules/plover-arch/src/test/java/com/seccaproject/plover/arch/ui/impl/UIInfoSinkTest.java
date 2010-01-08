package com.seccaproject.plover.arch.ui.impl;

import static org.junit.Assert.assertEquals;

import java.util.Locale;

import org.junit.Test;

import com.seccaproject.plover.arch.i18n.nls.DefaultDispatcher;

public class UIInfoSinkTest {

    UIInfoSink sink;

    public UIInfoSinkTest() {
        Locale.setDefault(new Locale("not-exist-locale"));

        sink = new UIInfoSink(UIInfoSinkTest.class);
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
