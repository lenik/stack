package com.bee32.zebra.tk.slim.cmd;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiServletCommand;
import net.bodz.bas.ui.model.cmd.Location;

import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.slim.SlimForm_htm;

/**
 * 打印
 * 
 * @tooltip 输出适合打印的格式。
 * @cmd.id printcmd
 * @cmd.href "../" + id + ".pdf"
 * @cmd.target _blank
 */
@FontAwesomeImage(SlimForm_htm.FA_PRINT)
@Location(ZpCmds1Toolbar.class)
public class PrintFormCommand
        extends UiServletCommand {

}