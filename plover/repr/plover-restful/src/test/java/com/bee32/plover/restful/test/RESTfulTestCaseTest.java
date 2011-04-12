package com.bee32.plover.restful.test;

import java.io.IOException;

import org.junit.Test;

import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.builtin.CoreInfoModule;

public class RESTfulTestCaseTest
        extends RESTfulTestCase {

    static final String corePath = "/" + OidUtil.getOid(CoreInfoModule.class).toPath();

    @Test
    public void testCoreInfoCreditText()
            throws Exception {
        String prefix = "http://localhost:" + stl.getPort() + PREFIX;
        String creditUri = prefix + corePath + "/credit";

        // System.out.println(creditUri);

        String credit = httpGet(creditUri).getContent();
        assertNotNull(credit);

        System.out.println(credit);
        assertTrue(credit.contains("Object Dump"));
    }

    public static void main(String[] args)
            throws IOException {
        new RESTfulTestCaseTest().browseAndWait();
    }

}
