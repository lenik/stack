package com.bee32.sem.world.monetary;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(MonetaryUnit.class)
public class FxrCommitJobTest
        // extends QuartzPlayer
        extends SEMTestCase {

    public static void main(String[] args)
            throws IOException {
        new FxrCommitJobTest().browseAndWait("/quartz/stat");
    }

}
