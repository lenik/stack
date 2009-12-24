package com.seccaproject.plover.arch.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Locale;

import org.junit.Test;

public class PlainRefdocsTest {

    public PlainRefdocsTest() {
        // disable default locale for testing purpose.
        Locale.setDefault(new Locale("no-default-locale"));
    }

    @Test
    public void testParseResource_en()
            throws Exception {
        PlainRefdocs refdocs = PlainRefdocs.parseResource(getClass(), Locale.getDefault());
        String[] tags = refdocs.getTags();

        // TreeMap required.
        assertArrayEquals(new String[] { "2009", "refspec", "simple", "tutorial", },//
                tags);

        IRefdocEntry tutorial = refdocs.getDefaultEntry("tutorial");
        assertEquals("Simple Tutorial", tutorial.getTitle());

        IRefdocEntry refspec = refdocs.getDefaultEntry("refspec");
        assertEquals("Reference Specification 2009", refspec.getTitle());

        InputStream in = refspec.getURL().openStream();
        byte[] block = new byte[1024];
        int cb = in.read(block);
        in.close();
        String content = new String(block, 0, cb, "utf-8");
        assertEquals("A test resource page. ", content);
    }

    @Test
    public void testParseResource_zh()
            throws Exception {
        PlainRefdocs refdocs = PlainRefdocs.parseResource(getClass(), Locale.SIMPLIFIED_CHINESE);
        String[] tags = refdocs.getTags();

        // TreeMap required.
        assertArrayEquals(new String[] { "2009", "refspec", "simple", "tutorial", },//
                tags);

        IRefdocEntry tutorial = refdocs.getDefaultEntry("tutorial");
        assertEquals("简单教程", tutorial.getTitle());

        IRefdocEntry refspec = refdocs.getDefaultEntry("refspec");
        assertEquals("Reference Specification 2009", refspec.getTitle());

        InputStream in = refspec.getURL().openStream();
        byte[] block = new byte[1024];
        int cb = in.read(block);
        in.close();
        String content = new String(block, 0, cb, "utf-8");
        assertEquals("测试页面。", content);
    }

}
