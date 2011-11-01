package com.bee32.sem.test;

import java.io.IOException;

import com.bee32.plover.orm.PloverORMUnit;
import com.bee32.plover.orm.unit.Using;

@Using(PloverORMUnit.class)
public class SEMMinimalTest
        extends SEMTestCase {

    public static void main(String[] args)
            throws IOException {
        new SEMMinimalTest().browseAndWait();
    }

}
