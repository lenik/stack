package com.bee32.icsf.access.alt;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.icsf.IcsfIdentityUnit;
import com.bee32.icsf.access.resource.AccessPointManager;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(IcsfIdentityUnit.class)
public class R_AuthorityTest
        extends WiredDaoTestCase {

    @Inject
    R_Authority authority;

    @Inject
    AccessPointManager apManager;

    @Test
    public void testBuildACL() {
        Resource resource = null;
        authority.getACL(resource);
    }

}
