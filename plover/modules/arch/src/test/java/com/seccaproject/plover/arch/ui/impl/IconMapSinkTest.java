package com.seccaproject.plover.arch.ui.impl;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Locale;

import org.junit.Test;

import com.seccaproject.plover.arch.i18n.nls.DefaultDispatcher;
import com.seccaproject.plover.arch.ui.IconVariantName;

public class IconMapSinkTest {

    IconMapSink sink;
    Class<?> clazz = IconMapSinkTest.class;

    public IconMapSinkTest() {
        sink = new IconMapSink(getClass());
        DefaultDispatcher dispatcher = new DefaultDispatcher(sink);
        dispatcher.visitClassResource(getClass(), Locale.ENGLISH);
    }

    @Test
    public void testImplicationSize()
            throws Exception {
        URL icon;
        icon = sink.getIcon(IconVariantName.HOVER, 16, 16);
        assertEquals(clazz.getResource("Hover-16x16"), icon);

        icon = sink.getIcon(IconVariantName.HOVER, 5, 5);
        assertEquals(clazz.getResource("Hover-16x16"), icon);

        icon = sink.getIcon(IconVariantName.HOVER, 25, 25);
        assertEquals(clazz.getResource("Hover-32x32"), icon);

        icon = sink.getIcon(IconVariantName.HOVER, 40, 40);
        assertEquals(clazz.getResource("Hover"), icon);

        icon = sink.getIcon(IconVariantName.HOVER);
        assertEquals(clazz.getResource("Hover"), icon);
    }

    @Test
    public void testImplicationStems()
            throws Exception {
        URL icon;
        icon = sink.getIcon("hover.disabled", 16, 16);
        assertEquals(clazz.getResource("HoverDisabled"), icon);

        icon = sink.getIcon("hover.nonexist", 16, 16);
        assertEquals(clazz.getResource("Hover-16x16"), icon);

        icon = sink.getIcon("hover.nonexist");
        assertEquals(clazz.getResource("Hover"), icon);
    }

    @Test
    public void testImplicationMixed()
            throws Exception {
        URL icon;
        icon = sink.getIcon("hover.nonexist", 32, 32);
        assertEquals(clazz.getResource("Hover-32x32"), icon);
    }

}
