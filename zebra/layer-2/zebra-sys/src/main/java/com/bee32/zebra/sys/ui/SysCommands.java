package com.bee32.zebra.sys.ui;

import net.bodz.bas.ui.model.cmd.CommandList;

public class SysCommands
        extends CommandList {

    {
        addCommand(new HelpAboutCommand());
    }

}
