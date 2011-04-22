package com.bee32.sem.process;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.builtins.MainMenu;

public class SEMProcessMenuTest
        extends WiredTestCase {

    @Inject
    MainMenu mainMenu;

    @Test
    public void testLoadNLS() {
        System.out.println(mainMenu);
    }

}
