package com.bee32.plover.arch.ui.res;

import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Test;

import com.bee32.plover.arch.ui.StandardImageVariant;
import com.bee32.plover.arch.util.ClassUtil;
import com.bee32.plover.arch.util.res.PropertyDispatcher;

public class InjectedImageMapTest {

    InjectedImageMap sink;
    Class<?> clazz = InjectedImageMapTest.class;

    public InjectedImageMapTest() {

        URL contextURL = ClassUtil.getContextURL(getClass());
        sink = new InjectedImageMap(contextURL);

        new PropertyDispatcher(getClass()).setRootAcceptor(sink).pull();
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
