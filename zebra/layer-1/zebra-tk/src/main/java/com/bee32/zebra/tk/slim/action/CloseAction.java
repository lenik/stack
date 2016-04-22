package com.bee32.zebra.tk.slim.action;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;
import net.bodz.lily.model.base.CoEntity;

import com.bee32.zebra.tk.site.ZpCmds0Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

/**
 * 关闭本页面
 * 
 * @cmd.onclick window.close()
 */
@FontAwesomeImage(SlimIndex_htm.FA_TIMES_CIRCLE)
@Location(ZpCmds0Toolbar.class)
public class CloseAction
        extends UiScriptAction {

    @Override
    public Class<?> getTargetClass() {
        return CoEntity.class;
    }

}