package com.bee32.sem.frame.builtins;

import org.junit.Test;

import com.bee32.plover.test.WiredTestCase;
import com.bee32.sem.frame.menu.ContextMenuAssembler;
import com.bee32.sem.frame.menu.IMenuAssembler;
import com.bee32.sem.frame.menu.IMenuNode;

public class MainMenuTest
        extends WiredTestCase {

    @Test
    public void testBuiltinNLS() {
        IMenuAssembler assembler = ContextMenuAssembler.getMenuAssembler();
        IMenuNode entry = assembler.require(SEMFrameMenu.class).MAIN.resolve("help/aboutFrame");
        assertNotNull(entry);

        String displayName = entry.getAppearance().getDisplayName();
        assertEquals("About SEM-Frame", displayName);
    }

    // @Override
    public void dump() {
        IMenuAssembler assembler = ContextMenuAssembler.getMenuAssembler();
        assembler.require(SEMFrameMenu.class).MAIN.getName();
    }

    public static void main(String[] args)
            throws Exception {
        new MainMenuTest().wire().dump();
    }

}
