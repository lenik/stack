package com.bee32.zebra.tk.slim;

import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;

/**
 * 打印
 * 
 * @cmd.id printcmd
 * @cmd.onclick window.print()
 */
@Location(ZpCmds0Toolbar.class)
public class PrintIndexAction
        extends UiScriptAction {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }
}