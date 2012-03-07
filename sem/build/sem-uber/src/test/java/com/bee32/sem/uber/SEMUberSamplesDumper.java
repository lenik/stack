package com.bee32.sem.uber;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.orm.sample.SampleDumper;
import com.bee32.plover.orm.sample.SamplePackage;
import com.bee32.plover.orm.sample.SuperSamplePackage.Normals;
import com.bee32.plover.test.WiredTestCase;

public class SEMUberSamplesDumper
        extends WiredTestCase {

    @Inject
    Normals normals;

    @Test
    public void dumpNormalSamples() {
        // scan&register all packages
        application.getBeansOfType(SamplePackage.class);
        SampleDumper.dump(normals);
    }

}
