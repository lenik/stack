package com.bee32.sem.frame.builtins;

import javax.inject.Inject;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.menu.IMenuNode;
import com.bee32.sem.frame.menu.MenuLoader;

public class MainMenuTest
        extends WiredTestCase {

    @Inject
    MenuLoader menuLoader;

    @Test
    public void testBuiltinNLS() {
        IMenuNode entry = SEMFrameMenu.MAIN.resolve("help/aboutFrame");
        assertNotNull(entry);

        String displayName = entry.getAppearance().getDisplayName();
        assertEquals("About SEM-Frame", displayName);
    }

    // @Override
    public void dump() {
        SEMFrameMenu.MAIN.getName();
    }

    public static void main(String[] args)
            throws Exception {
        new MainMenuTest().wire().dump();
    }

}
