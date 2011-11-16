package com.bee32.plover.rtx.location;

import org.junit.Assert;
import org.junit.Test;

public class LocationsTest
        extends Assert
        implements ILocationConstants {

    static Location PARENT = WEB_APP.join("parent/");
    static Location CHILD = PARENT.join("child/");
    static Location FILE = CHILD.join("dir/test.png");

    static String fileQualified = "Servlet-Context::/parent/child/dir/test.png";

    @Test
    public void testQualify() {
        String _file = Locations.qualify(FILE);
        assertEquals(fileQualified, _file);
    }

    @Test
    public void testParse() {
        Location file = Locations.parse(fileQualified);
        assertEquals(FILE, file);
    }

}
