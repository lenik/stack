package com.bee32.zebra.tk.slim.cmd;

import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.ui.model.cmd.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;

/**
 * 新建
 * 
 * @cmd.href new/
 * @cmd.target _blank
 */
@Location(ZpCmds0Toolbar.class)
public class NewCommand
        extends UiServletCommand {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }

}