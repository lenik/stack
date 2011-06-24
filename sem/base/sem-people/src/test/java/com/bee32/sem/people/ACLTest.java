package com.bee32.sem.people;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.icsf.access.resource.IResourceNamespace;
import com.bee32.icsf.access.resource.Resource;
import com.bee32.icsf.access.resource.ScannedResourceRegistry;
import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.orm.util.WiredDaoTestCase;

@Using(SEMPeopleUnit.class)
public class ACLTest
        extends WiredDaoTestCase {

    @Inject
    ScannedResourceRegistry srr;

    @Test
    public void test1() {
        for (IResourceNamespace rn : srr.getNamespaces()) {
            System.err.println("RN: " + rn.getNamespace());
            for (Resource r : rn.getResources()) {
                System.err.println("  - " + r.getName() + ": " + r.getAppearance().getDisplayName());
            }
        }
    }

}
