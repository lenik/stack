package com.bee32.sem.file;

import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMFileMenu
        extends MenuContribution {

    static Location FILE_ = WEB_APP.join(SEMFileModule.PREFIX_);

    static MenuNode userFile = entry(SEMFrameMenu.START, 40, "file", FILE_.join("file/"));

    @Override
    protected void preamble() {
    }

}
