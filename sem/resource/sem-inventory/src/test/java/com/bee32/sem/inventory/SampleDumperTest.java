package com.bee32.sem.inventory;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.sample.SampleDumper;
import com.bee32.plover.orm.sample.SuperSamplePackage.Everythings;
import com.bee32.plover.test.WiredTestCase;

public class SampleDumperTest
        extends WiredTestCase {

    @Inject
    Everythings everythingGroup;
    @Inject
    SEMInventorySamples inventorySamples;

    @Test
    public void testDumpEverything() {
        SampleDumper.dump(everythingGroup);
    }

    @Test
    public void testInventorySamples() {
        SEMInventorySamples pkg = inventorySamples;
        SampleDumper.checkUnique(pkg);
        SampleDumper.dump(pkg);

        // SampleDumper.checkUnique(VirtualSamplePackage.EVERYTHING);
        // SampleDumper.dump(VirtualSamplePackage.EVERYTHING);
    }

}
