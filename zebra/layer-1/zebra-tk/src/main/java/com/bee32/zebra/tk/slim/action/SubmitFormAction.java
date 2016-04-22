package com.bee32.zebra.tk.slim.action;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

/**
 * 提交
 * 
 * @tooltip 将输入的数据提交保存。
 * 
 * @cmd.onclick form1.submit()
 */
@FontAwesomeImage(SlimIndex_htm.FA_FLOPPY_O)
@Location(ZpCmds1Toolbar.class)
public class SubmitFormAction
        extends UiScriptAction {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }
}