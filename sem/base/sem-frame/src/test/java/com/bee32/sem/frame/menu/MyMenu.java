package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.builtins.SEMFrameMenu;

public class MyMenu
        extends MenuContribution {

    public static MenuNode FILE = menu(SEMFrameMenu.MAIN, "file");

    static MenuNode OPEN = entry(FILE, "open", WEB_APP.join("file/open.do"));
    static MenuNode SAVE = entry(FILE, "save", WEB_APP.join("file/save.do"));
    static MenuNode CLOSE = entry(FILE, "close", WEB_APP.join("file/close.do"));

    @Override
    protected void preamble() {
    }

}