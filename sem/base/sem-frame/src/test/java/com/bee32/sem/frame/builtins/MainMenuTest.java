package com.bee32.sem.frame.builtins;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.menu.IMenuNode;

public class MainMenuTest
        extends WiredTestCase {

    @Test
    public void testBuiltinNLS() {
        IMenuNode entry = SEMFrameMenu.getMainMenu().resolve("help/aboutFrame");
        assertNotNull(entry);

        String displayName = entry.getAppearance().getDisplayName();
        assertEquals("About SEM-Frame", displayName);
    }

    // @Override
    public void dump() {
        SEMFrameMenu.getMainMenu().getName();
    }

    public static void main(String[] args)
            throws Exception {
        new MainMenuTest().wire().dump();
    }

}
