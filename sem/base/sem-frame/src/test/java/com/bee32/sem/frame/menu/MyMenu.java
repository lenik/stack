package com.bee32.sem.frame.menu;

import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.action.Action;

public class MyMenu
        extends MenuContribution {

    Action openFileAction = new Action(WEB_APP.join("file/open/htm"));

    @Contribution(".")
    MenuItem file = new MenuItem("file");

    @Contribution("file")
    MenuItem fileOpen = new MenuItem("open", openFileAction);

    @Override
    protected void preamble() {
    }

}