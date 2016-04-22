package com.bee32.zebra.sys.ui;

import net.bodz.bas.ui.model.action.MutableActionProvider;

public class SysCommands
        extends MutableActionProvider {

    {
        addAction(new HelpAboutAction());
    }

}
