package com.bee32.sem.file;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFileMenu
        extends MenuComposite {

    static Location __ = WEB_APP.join(SEMFileModule.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    MenuNode FILE = menu(_frame_.START, 1000, "FILE");
    /**/MenuNode folder = entry(FILE, 200, "folder", __.join("folder/"));
    /**/MenuNode userFile = entry(FILE, 300, "userFile", __.join("file/"));

    /**/MenuNode SETTINGS = menu(FILE, 1000, "SETTINGS");
    /*    */MenuNode fileTag = entry(SETTINGS, 10, "fileTag", __.join("fileTag/"));

}
