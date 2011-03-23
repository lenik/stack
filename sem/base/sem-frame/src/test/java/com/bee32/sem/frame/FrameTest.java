package com.bee32.sem.frame;

import javax.inject.Inject;

import com.bee32.sem.frame.builtins.ContextMenu;
import com.bee32.sem.frame.menu.IMenuEntry;

public class FrameTest {

    @Inject
    IMainFrame frame;

    public FrameTest() {

        IMenuEntry menu = frame.getMainFrame().getMenu(ContextMenu.CONTEXT_NETWORK);
//        menu.contribute("asd/foo", new MenuItem());
    }

}
