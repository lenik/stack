package com.bee32.zebra.tk.slim.action;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;
import net.bodz.lily.model.base.CoObject;

import com.bee32.zebra.tk.site.ZpCmds1Toolbar;
import com.bee32.zebra.tk.slim.SlimForm_htm;

/**
 * 输出适合打印的格式。
 * 
 * @label 打印
 * @cmd.id printcmd
 * @cmd.target _blank
 */
@FontAwesomeImage(SlimForm_htm.FA_PRINT)
@Location(ZpCmds1Toolbar.class)
public class PrintFormAction
        extends UiScriptAction {

    public PrintFormAction() {
        addScript("href");
    }

    @Override
    public String getScript(String scriptId, Object obj) {
        switch (scriptId) {
        case "href":
            CoObject o = (CoObject) obj;
            Object id = o.getId();
            return "../" + id + ".pdf";
        }
        return super.getScript(scriptId, obj);
    }

}