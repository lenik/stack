package com.bee32.sem.frame.menu;

public class MyMenu
        extends MenuContribution {

    public static MenuNode FILE = menu(SEMFrameMenu.MAIN, "file");

    static MenuNode fileOpen = entry(FILE, "open", WEB_APP.join("file/open.do"));
    static MenuNode fileSave = entry(FILE, "save", WEB_APP.join("file/save.do"));
    static MenuNode fileClose = entry(FILE, "close", WEB_APP.join("file/close.do"));

    @Override
    protected void preamble() {
    }

}