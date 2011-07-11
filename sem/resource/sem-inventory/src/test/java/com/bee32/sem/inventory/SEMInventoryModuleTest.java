package com.bee32.sem.inventory;

import java.io.IOException;

import com.bee32.plover.orm.unit.Using;
import com.bee32.sem.test.SEMTestCase;

@Using(SEMInventoryUnit.class)
public class SEMInventoryModuleTest
        extends SEMTestCase {

    public static void main(String[] args)
            throws IOException {
        new SEMInventoryModuleTest().browseAndWait(SEMInventoryModule.PREFIX + "/material/");
    }

}
