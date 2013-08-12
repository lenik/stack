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

    /**
     * 事件跟踪
     *
     * <p lang="en">
     * Issue Tracking
     */
    public MenuNode TRACK = menu(_frame_.OA, 30, "TRACK");

    /**
     * 我发起的事件
     *
     * <p lang="en">
     * My Issues
     */
    MenuNode my = entry(TRACK, 10, "my", prefix.join("/issue/my"));

    /**
     * 我参与的事件
     *
     * <p lang="en">
     * Observed Issues
     */
    MenuNode inbox = entry(TRACK, 20, "inbox", prefix.join("/issue/inbox"));

    /**
     * 历史事件
     *
     * <p lang="en">
     * Archived Issues
     */
    MenuNode archived = entry(TRACK, 30, "archived", prefix.join("/issue/archived"));

}
