package com.bee32.sem.file;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFileMenu
        extends MenuContribution {

    static Location FILE_ = WEB_APP.join(SEMFileModule.PREFIX_);

    public static MenuNode FILE = menu(SEMFrameMenu.BASE, "file");

    static MenuNode personAdmin = entry(FILE, 1, "fileUpload", FILE_.join("index-rich.jsf"));


    @Override
    protected void preamble() {
    }

}
