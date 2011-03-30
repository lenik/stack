package com.bee32.sem.frame.menu;

import java.io.IOException;

import com.bee32.plover.zk.test.ZkTestCase;
import com.bee32.sem.frame.web.SuperfishMenuController;

/**
 * @see SuperfishMenuBuilder
 * @see SuperfishMenuController
 */
public class SuperfishMenuBuilderTest
        extends ZkTestCase {

    public static void main(String[] args)
            throws IOException, Exception {
        new SuperfishMenuBuilderTest().wire().browseAndWait("sfmenuDemo.htm");
    }

}
