package com.bee32.plover.restful.test;

import org.junit.Test;

import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.builtin.CoreInfoModule;
import com.bee32.plover.test.AssembledTestCase;

public class RestfulTesterLibraryTest
        extends AssembledTestCase {

    static final String corePath = OidUtil.getOid(CoreInfoModule.class).toPath();

    RestfulTesterLibrary rtl;

    public RestfulTesterLibraryTest() {
        install(rtl = new RestfulTesterLibrary());
    }

    @Test
    public void testCoreInfoCreditText()
            throws Exception {
        String prefix = "http://localhost:" + rtl.getPort() + "/";
        String creditUri = prefix + corePath + "/credit";

        // System.out.println(creditUri);

        String credit = rtl.httpGet(creditUri).getContent();
        assertNotNull(credit);

        System.out.println(credit);
        assertTrue(credit.contains("99"));
    }

}
