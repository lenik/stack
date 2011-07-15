package com.bee32.sem.inventory;

import org.junit.Test;

import com.bee32.plover.orm.util.ImportSamplesUtil;
import com.bee32.plover.orm.util.SamplesLoader;
import com.bee32.plover.orm.util.VirtualSamplePackage;
import com.bee32.plover.test.WiredTestCase;
import com.bee32.plover.web.faces.view.Inject;

public class SampleDumperTest
        extends WiredTestCase {

    @Inject
    SamplesLoader samplesLoader;

    @Test
    public void testDumpEverything() {
        SampleDumper.dump(VirtualSamplePackage.EVERYTHING);
    }

    public static void main(String[] args) {
        SEMInventorySamples a = new SEMInventorySamples();
        ImportSamplesUtil.applyImports(a);

        SampleDumper.checkUnique(a);
        SampleDumper.dump(a);

        // SampleDumper.checkUnique(VirtualSamplePackage.EVERYTHING);
        // SampleDumper.dump(VirtualSamplePackage.EVERYTHING);
    }

}
