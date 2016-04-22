package com.bee32.zebra.oa.file.impl;

import net.bodz.bas.html.util.FontAwesomeImage;
import net.bodz.bas.http.ui.cmd.UiScriptAction;
import net.bodz.bas.ui.model.action.Location;

import com.bee32.zebra.oa.file.FileInfo;
import com.bee32.zebra.tk.site.ZpLinksToolbar;

/**
 * 上传
 * 
 * @cmd.onclick uploadDialog.open()
 */
@FontAwesomeImage(FileInfo_htm.FA_UPLOAD)
@Location(ZpLinksToolbar.class)
public class UploadAction
        extends UiScriptAction {

    @Override
    public Class<?> getTargetClass() {
        return FileInfo.class;
    }

}