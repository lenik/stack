package com.bee32.zebra.tk.slim.action;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

/**
 * 重新载入当前页面。
 * 
 * @label 刷新
 * @cmd.href javascript: location.href = location.href
 * @cmd.onclick spin(this)
 */
@FontAwesomeImage(SlimIndex_htm.FA_REFRESH)
@Location(ZpCmds1Toolbar.class)
public class RefreshAction
        extends UiScriptAction {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }
}