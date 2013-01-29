package com.bee32.ape.html.apex;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;

public class ApexTestServer
        extends FaceletsTestCase {

    public static void main(String[] args)
            throws IOException {
        new ApexTestServer().browseAndWait();
    }

}
