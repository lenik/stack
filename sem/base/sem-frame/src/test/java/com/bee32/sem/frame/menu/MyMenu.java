package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.builtins.SEMFrameMenu;

public class MyMenu
        extends MenuContribution {

    public static MenuNode FILE = SEMFrameMenu.FILE;

    static MenuNode OPEN = entry(FILE, 10, "open", WEB_APP.join("file/open.do"));
    static MenuNode SAVE = entry(FILE, 20, "save", WEB_APP.join("file/save.do"));
    static MenuNode CLOSE = entry(FILE, 30, "close", WEB_APP.join("file/close.do"));

    @Override
    protected void preamble() {
    }

}