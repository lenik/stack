package com.seccaproject.plover.arch.ui;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.junit.Test;

public class RefdocsBuilderTest {

    URL url1;
    URL url2;

    public RefdocsBuilderTest()
            throws MalformedURLException {
        url1 = new URL("http://example.com/url1");
        url2 = new URL("http://example.com/url2");
    }

    @Test
    public void testAdd()
            throws Exception {

        RefdocsBuilder hi = new RefdocsBuilder();
        hi.add(new RefdocEntry(url1, "tag1", "tag2"));
        hi.add(new RefdocEntry(url2, "tag2"));
        assertEquals(url1, hi.getDefaultEntry("tag1").getURL());
        assertEquals(url1, hi.getDefaultEntry("tag2").getURL());

        Iterator<? extends IRefdocEntry> entriesIterator = hi.getEntries("tag2");
        assertEquals(url1, entriesIterator.next().getURL());
        assertEquals(url2, entriesIterator.next().getURL());
    }

}
