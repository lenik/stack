package com.bee32.sem.uber;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.util.DiamondPackage.NormalGroup;
import com.bee32.plover.orm.util.SampleDumper;
import com.bee32.plover.test.WiredTestCase;

public class SEMUberSamplesDumper
        extends WiredTestCase {

    @Inject
    NormalGroup normals;

    @Test
    public void dumpNormalSamples() {
        SampleDumper.dump(normals);
    }

}
