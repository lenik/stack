package com.bee32.sem.track;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMTrackMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);
    static Location prefix = WEB_APP.join(SEMTrackModule.PREFIX);

    public MenuNode TRACK = menu(_frame_.OA, 30, "TRACK");

    MenuNode my = entry(TRACK, 10, "my", prefix.join("/issue/my"));
    MenuNode inbox = entry(TRACK, 20, "inbox", prefix.join("/issue/inbox"));
    MenuNode archived = entry(TRACK, 30, "archived", prefix.join("/issue/archived"));

}
