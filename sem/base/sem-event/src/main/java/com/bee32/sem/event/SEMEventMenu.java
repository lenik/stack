package com.bee32.sem.event;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.event.web.EventController;
import com.bee32.sem.event.web.EventPriorityController;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMEventMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location EVENT_ = WEB_APP.join(EventController.PREFIX_);

    public static MenuNode EVENT = menu(SEMFrameMenu.FILE, "event");
    public static MenuNode ADMIN = menu(EVENT, "eventAdmin"); // MAIN/Admin

    static MenuNode categories = entry(ADMIN, 10, "categories", getDictIndex(EventCategory.class));
    static MenuNode priorities = entry(ADMIN, 11, "priorities", //
            WEB_APP.join(EventPriorityController.PREFIX).join("index.do"));

    static MenuNode states = entry(ADMIN, 12, "states", getDictIndex(EventStatus.class));

    static MenuNode pendingTasks = entry(EVENT, 10, "pendingTasks", EVENT_.join("index.do?type=t&closed=false"));
    static MenuNode completedTasks = entry(EVENT, 11, "completedTasks", EVENT_.join("index.do?type=t&closed=true"));
    static MenuNode eventIndex = entry(EVENT, 20, "eventIndex", EVENT_.join("index.do?recent=30"));

    @Override
    protected void preamble() {
    }

}
