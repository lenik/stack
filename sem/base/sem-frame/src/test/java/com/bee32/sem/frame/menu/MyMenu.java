package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.builtins.SEMFrameMenu;

public class MyMenu
        extends MenuComposite {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode FILE = _frame_.START;

    MenuNode OPEN = entry(FILE, 10, "open", WEB_APP.join("file/open.do"));
    MenuNode SAVE = entry(FILE, 20, "save", WEB_APP.join("file/save.do"));
    MenuNode CLOSE = entry(FILE, 30, "close", WEB_APP.join("file/close.do"));

}