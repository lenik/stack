package com.bee32.icsf.access.alt;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.icsf.IcsfAccessUnit;
import com.bee32.icsf.access.acl.IACL;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(IcsfAccessUnit.class)
// XXX @ImportSamples(R_ACLSamples.class)
public class R_AuthorityTest
        extends WiredDaoTestCase {

    @Inject
    R_Authority authority;

    @Inject
    ScannedResourceRegistry registry;

    @Test
    public void testQueryACL() {
        Resource method1 = registry.query("ap:TestService.method1");
        if (method1 == null)
            throw new NullPointerException("method1");

        IACL acl = authority.getACL(method1);
        System.out.println(acl);
    }

}
