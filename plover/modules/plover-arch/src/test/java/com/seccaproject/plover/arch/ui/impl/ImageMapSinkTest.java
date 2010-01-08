package com.seccaproject.plover.arch.ui.impl;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.Locale;

import org.junit.Test;

import com.seccaproject.plover.arch.i18n.nls.DefaultDispatcher;
import com.seccaproject.plover.arch.ui.StandardImageVariant;

public class ImageMapSinkTest {

    ImageMapSink sink;
    Class<?> clazz = ImageMapSinkTest.class;

    public ImageMapSinkTest() {
        sink = new ImageMapSink(getClass());
        DefaultDispatcher dispatcher = new DefaultDispatcher(sink);
        dispatcher.visitClassResource(getClass(), Locale.ENGLISH);
    }

    @Test
    public void testImplicationSize()
            throws Exception {
        URL icon;
        icon = sink.getImage(StandardImageVariant.HOVER, 16, 16);
        assertEquals(clazz.getResource("Hover-16x16"), icon);

        icon = sink.getImage(StandardImageVariant.HOVER, 5, 5);
        assertEquals(clazz.getResource("Hover-16x16"), icon);

        icon = sink.getImage(StandardImageVariant.HOVER, 25, 25);
        assertEquals(clazz.getResource("Hover-32x32"), icon);

        icon = sink.getImage(StandardImageVariant.HOVER, 40, 40);
        assertEquals(clazz.getResource("Hover"), icon);

        icon = sink.getImage(StandardImageVariant.HOVER);
        assertEquals(clazz.getResource("Hover"), icon);
    }

    @Test
    public void testImplicationStems()
            throws Exception {
        URL icon;
        icon = sink.getImage("hover.disabled", 16, 16);
        assertEquals(clazz.getResource("HoverDisabled"), icon);

        icon = sink.getImage("hover.nonexist", 16, 16);
        assertEquals(clazz.getResource("Hover-16x16"), icon);

        icon = sink.getImage("hover.nonexist");
        assertEquals(clazz.getResource("Hover"), icon);
    }

    @Test
    public void testImplicationMixed()
            throws Exception {
        URL icon;
        icon = sink.getImage("hover.nonexist", 32, 32);
        assertEquals(clazz.getResource("Hover-32x32"), icon);
    }

}
