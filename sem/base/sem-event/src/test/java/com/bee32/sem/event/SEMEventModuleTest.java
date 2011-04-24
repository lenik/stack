package com.bee32.sem.event;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMEventUnit.class)
public class SEMEventModuleTest
        extends SEMTestCase {

    public static void main(String[] args)
            throws IOException {

        new SEMEventModuleTest().browseAndWait(//
                SEMEventMenu.EVENT.join("index.htm").getBase() //
                // SEMEventMenu.DICT.join(EventState.class.getName() + "/index.htm").getBase() //
                );
    }
}
