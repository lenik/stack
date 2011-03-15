package com.bee32.plover.restful.test;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.bee32.plover.inject.LegacyContext;
import com.bee32.plover.pub.oid.OidUtil;
import com.bee32.plover.restful.builtin.CoreInfoModule;
import com.bee32.plover.test.AssembledTestCase;

/**
 * Should set RootWebAppContext.
 */
@Ignore
public class RestfulTesterLibraryTest
        extends AssembledTestCase {

    static final String corePath = OidUtil.getOid(CoreInfoModule.class).toPath();

    RestfulTesterLibrary rtl;

    public RestfulTesterLibraryTest() {
        ApplicationContext context = new LegacyContext().getApplicationContext();
        install(rtl = new RestfulTesterLibrary(context));
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
