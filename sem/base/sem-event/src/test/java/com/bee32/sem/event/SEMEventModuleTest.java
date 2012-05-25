package com.bee32.sem.event;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.plover.rtx.location.ILocationConstants;
import com.bee32.sem.event.web.EventController;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMEventUnit.class)
public class SEMEventModuleTest
        extends SEMTestCase
        implements ILocationConstants {

    @Override
    protected String getLoggedInUser() {
        return "Eva";
    }

    public static void main(String[] args)
            throws IOException {

        new SEMEventModuleTest().browseAndWait(//
                WEB_APP.join(EventController.PREFIX_).join("index.do").getBase() //
                // SEMEventMenu.DICT.join(EventState.class.getName() + "/index.do").getBase() //
                );
    }
}
