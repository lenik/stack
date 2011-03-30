package com.bee32.sem.frame.builtins;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.menu.IMenuNode;

public class MainMenuTest
        extends WiredTestCase {

    @Inject
    MainMenu mainMenu;

    @Test
    public void testBuiltinNLS() {
        IMenuNode entry = mainMenu.resolve("help/aboutFrame");
        assertNotNull(entry);

        String displayName = entry.getAppearance().getDisplayName();
        assertEquals("About SEM-Frame", displayName);
    }

    //@Override
    public void dump() {
        mainMenu.getName();
    }

    public static void main(String[] args) {
        //new MainMenuTest().wire().dump();
    }

}
