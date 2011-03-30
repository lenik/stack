package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.Contribution;

public class MyMenu
        extends MenuContribution {

    @Contribution(".")
    MenuEntry file = new MenuEntry("file");

    @Contribution("file")
    MenuEntry fileOpen = new MenuEntry("open", WEB_APP.join("file/open.htm"));

    @Override
    protected void preamble() {
    }

}