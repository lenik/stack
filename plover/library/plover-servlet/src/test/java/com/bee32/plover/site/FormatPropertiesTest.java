package com.bee32.plover.site;

import javax.free.StringSource;

import org.junit.Assert;
import org.junit.Test;

public class FormatPropertiesTest
        extends Assert {

    @Test
    public void testRewrite()
            throws Exception {
        String sample = " a=1\n b =OLD    # this is b  \n    c =    3\n";
        StringSource source = new StringSource(sample);
        FormatProperties properties = FormatProperties.parse("sample", source);

        String rewrite = properties.toString();
        assertEquals(sample, rewrite);

        properties.setProperty("b", "NEW");
        rewrite = properties.toString();
        String s2 = sample.replace("OLD", "NEW");
        assertEquals(s2, rewrite);
    }

}
