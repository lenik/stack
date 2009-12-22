package com.seccaproject.plover.arch.ui;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

public class HelpIndexTest {

    URL url1;
    URL url2;

    public HelpIndexTest()
            throws MalformedURLException {
        url1 = new URL("http://example.com/url1");
        url2 = new URL("http://example.com/url2");
    }

    @Test
    public void testAdd()
            throws Exception {

        HelpIndex hi = new HelpIndex();
        hi.add(new HelpEntry(url1, "tag1", "tag2"));
        hi.add(new HelpEntry(url2, "tag2"));
        assertEquals(url1, hi.getPreferredEntry("tag1").getURL());
        assertEquals(url1, hi.getPreferredEntry("tag2").getURL());

        Collection<? extends IHelpEntry> entries = hi.getEntries("tag2");
        Iterator<? extends IHelpEntry> iterator = entries.iterator();
        assertEquals(url1, iterator.next().getURL());
        assertEquals(url2, iterator.next().getURL());
    }

}
