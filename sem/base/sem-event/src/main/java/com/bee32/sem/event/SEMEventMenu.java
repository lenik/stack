package com.bee32.sem.event;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.event.web.EventController;
import com.bee32.sem.event.web.EventPriorityController;
import com.bee32.sem.frame.builtins.SEMFrameMenu;
import com.bee32.sem.frame.menu.MenuComposite;
import com.bee32.sem.frame.menu.MenuNode;

public class SEMEventMenu
        extends MenuComposite
        implements ITypeAbbrAware {

    static Location __ = WEB_APP.join(EventController.PREFIX_);
    SEMFrameMenu _frame_ = require(SEMFrameMenu.class);

    public MenuNode EVENT = menu(_frame_.START, 21, "EVENT");
    public MenuNode ADMIN = menu(EVENT, 100, "ADMIN"); // MAIN/Admin

    MenuNode categories = entry(ADMIN, 10, "categories", getDictIndex(EventCategory.class));
    MenuNode priorities = entry(ADMIN, 11, "priorities", //
            WEB_APP.join(EventPriorityController.PREFIX_).join("index.do"));
    MenuNode states = entry(ADMIN, 12, "states", getDictIndex(EventStatus.class));

    MenuNode pendingTasks = entry(EVENT, 10, "pendingTasks", __.join("index.do?type=t&closed=false"));
    MenuNode completedTasks = entry(EVENT, 11, "completedTasks", __.join("index.do?type=t&closed=true"));
    MenuNode eventIndex = entry(EVENT, 20, "eventIndex", __.join("index.do?recent=30"));

}
