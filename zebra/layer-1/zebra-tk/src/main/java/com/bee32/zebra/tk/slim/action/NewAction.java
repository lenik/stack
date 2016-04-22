package com.bee32.zebra.tk.slim.action;

import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;

/**
 * 新建
 * 
 * @cmd.href new/
 * @cmd.target _blank
 */
@Location(ZpCmds0Toolbar.class)
public class NewAction
        extends UiScriptAction {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }

}