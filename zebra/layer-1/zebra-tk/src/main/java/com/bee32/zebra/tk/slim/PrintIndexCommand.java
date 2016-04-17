package com.bee32.zebra.tk.slim;

import net.bodz.bas.http.ui.cmd.UiServletCommand;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;

/**
 * 打印
 * 
 * @cmd.id printcmd
 * @cmd.href javascript: window.print()
 */
public class PrintIndexCommand
        extends UiServletCommand {
    {
        addLocation(ZpCmds0Toolbar.class);
    }

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }
}