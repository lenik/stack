package com.seccaproject.plover.arch.ui;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Locale;

import org.junit.Test;

public class ConstHelpIndexTest {

    @Test
    public void testParseResource()
            throws Exception {
        ConstHelpIndex chi = ConstHelpIndex.parseResource(getClass(), Locale.getDefault());
        String[] tags = chi.getTags();

        // TreeMap required.
        assertArrayEquals(new String[] { "2009", "refspec", "simple", "tutorial", },//
                tags);

        IHelpEntry refspec = chi.getPreferredEntry("refspec");
        assertEquals("Reference Specification 2009", refspec.getTitle());

        InputStream in = refspec.getURL().openStream();
        byte[] block = new byte[1024];
        int cb = in.read(block);
        in.close();
        String content = new String(block, 0, cb, "utf-8");
        assertEquals("A test resource page. ", content);
    }

}
