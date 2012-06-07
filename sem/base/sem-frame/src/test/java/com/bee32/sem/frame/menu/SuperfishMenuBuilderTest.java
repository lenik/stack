package com.bee32.sem.frame.menu;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;
import com.bee32.sem.frame.web.SuperfishMenuController;

/**
 * @see SuperfishMenuBuilder
 * @see SuperfishMenuController
 */
public class SuperfishMenuBuilderTest
        extends FaceletsTestCase {

    public static void main(String[] args)
            throws IOException, Exception {
        // new SuperfishMenuBuilderTest().browseAndWait("sfmenuDemo.jsf");

        SuperfishMenuBuilder builder = new SuperfishMenuBuilder(null /* main-menu */);
        String html = builder.toString();
        System.out.println(html);
    }

}
