package com.bee32.sem.frame.menu;

import java.io.IOException;

import com.bee32.plover.faces.test.FaceletsTestCase;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
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

        new SEMFrameMenu().prepareMap();
        SuperfishMenuBuilder builder = new SuperfishMenuBuilder(SEMFrameMenu.MAIN);
        String html = builder.toString();
        System.out.println(html);
    }

}
