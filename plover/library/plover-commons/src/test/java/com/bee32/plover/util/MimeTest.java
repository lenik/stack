package com.bee32.plover.util;

import org.junit.Assert;
import org.junit.Test;

public class MimeTest
        extends Assert {

    @Test
    public void testEquality() {
        Mime html = new Mime("text/html", "html");
        assertEquals(Mimes.text_html, html);
    }

}
