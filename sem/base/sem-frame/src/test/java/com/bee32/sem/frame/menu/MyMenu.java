package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.Contribution;

public class MyMenu
        extends MenuContribution {

    @Contribution(".")
    MenuEntry file = new MenuEntry("file");

    @Contribution("file")
    MenuEntry fileOpen = new MenuEntry("open", WEB_APP.join("file/open.htm"));

    @Contribution("file")
    MenuEntry fileSave = new MenuEntry("save", WEB_APP.join("file/save.htm"));

    @Contribution("file")
    MenuEntry fileClose = new MenuEntry("close", WEB_APP.join("file/close.htm"));

    @Override
    protected void preamble() {
    }

}