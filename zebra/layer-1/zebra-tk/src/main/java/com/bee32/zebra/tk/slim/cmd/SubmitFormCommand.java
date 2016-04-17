package com.bee32.zebra.tk.slim.cmd;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.ui.model.cmd.Location;

import com.bee32.zebra.tk.repr.QuickIndex;
import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.slim.SlimIndex_htm;

/**
 * 提交
 * 
 * @tooltip 将输入的数据提交保存。
 * 
 * @cmd.href javascript: form1.submit()
 */
@FontAwesomeImage(SlimIndex_htm.FA_FLOPPY_O)
@Location(ZpCmds1Toolbar.class)
public class SubmitFormCommand
        extends UiServletCommand {

    @Override
    public Class<?> getTargetClass() {
        return QuickIndex.class;
    }
}