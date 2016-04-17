package com.bee32.zebra.tk.slim.cmd;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.ui.model.cmd.Location;
import net.bodz.lily.model.base.CoEntity;

import com.bee32.zebra.tk.site.ZpCmds0Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

/**
 * 关闭本页面
 * 
 * @cmd.href javascript: window.close()
 */
@FontAwesomeImage(SlimIndex_htm.FA_TIMES_CIRCLE)
@Location(ZpCmds0Toolbar.class)
public class CloseCommand
        extends UiServletCommand {

    @Override
    public Class<?> getTargetClass() {
        return CoEntity.class;
    }

}