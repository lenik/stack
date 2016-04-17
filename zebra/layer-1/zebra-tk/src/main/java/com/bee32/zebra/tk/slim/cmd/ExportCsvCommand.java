package com.bee32.zebra.tk.slim.cmd;

import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.ui.model.cmd.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;

/**
 * 导出
 * 
 * @style cursor: default; color: gray
 * @cmd.href ?view:=csv
 * @cmd.onclick return false
 */
@Location(ZpCmds0Toolbar.class)
public class ExportCsvCommand
        extends UiServletCommand {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }

}