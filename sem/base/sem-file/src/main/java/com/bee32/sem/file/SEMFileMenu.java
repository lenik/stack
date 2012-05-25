package com.bee32.sem.file;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFileMenu
        extends MenuComposite {

    static Location FILE_ = WEB_APP.join(SEMFileModule.PREFIX_);

    static MenuNode FILE = menu(SEMFrameMenu.START, 1000, "file");
    /**/static MenuNode userFile = entry(FILE, 40, "userFile", FILE_.join("file/"));

    /**/static MenuNode SETTINGS = menu(FILE, 1000, "settings");
    /*    */static MenuNode fileTag = entry(SETTINGS, 10, "fileTag", FILE_.join("fileTag/"));

}
