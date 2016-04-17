package com.bee32.zebra.tk.slim.cmd;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.ui.model.cmd.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds0Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

/**
 * 回到上一层
 * 
 * @cmd.href ../
 */
@FontAwesomeImage(SlimIndex_htm.FA_LEVEL_UP)
@Location(ZpCmds0Toolbar.class)
public class GoUpCommand
        extends UiServletCommand {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }

}