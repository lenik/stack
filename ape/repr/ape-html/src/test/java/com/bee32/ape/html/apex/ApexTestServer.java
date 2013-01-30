package com.bee32.ape.html.apex;

import java.io.IOException;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.icsf.test.LoginedTestCase;
import com.bee32.plover.orm.unit.Using;

@Using(IcsfAccessUnit.class)
public class ApexTestServer
        extends LoginedTestCase {
    // extends FaceletsTestCase {

    public static void main(String[] args)
            throws IOException {
        ApexTestServer server = new ApexTestServer();
        server.stl.initialize();
        server.stl.dumpXML();
        server.browseAndWait();
    }

}
