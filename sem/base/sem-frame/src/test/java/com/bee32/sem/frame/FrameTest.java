package com.bee32.sem.frame;

import javax.inject.Inject;

import com.bee32.sem.frame.context.ContextMenu;
import com.bee32.sem.frame.menu.IMenu;

public class FrameTest {

    @Inject
    IMainFrame frame;

    public FrameTest() {

        IMenu menu = frame.getMainFrame().getMenu(ContextMenu.CONTEXT_NETWORK);
//        menu.contribute("asd/foo", new MenuItem());
    }

}
