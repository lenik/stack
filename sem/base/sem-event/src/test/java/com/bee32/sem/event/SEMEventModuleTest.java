package com.bee32.sem.event;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMEventUnit.class)
public class SEMEventModuleTest
        extends SEMTestCase {

    @Override
    protected String getLoggedInUser() {
        return "Eva";
    }

    public static void main(String[] args)
            throws IOException {

        new SEMEventModuleTest().browseAndWait(//
                SEMEventMenu.EVENT_.join("index.do").getBase() //
                // SEMEventMenu.DICT.join(EventState.class.getName() + "/index.do").getBase() //
                );
    }

}
