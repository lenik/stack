package com.bee32.sem.event;

import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.rtx.location.Location;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.event.web.EventController;
import com.bee32.sem.event.web.EventPriorityController;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuNode;
import com.bee32.sem.frame.menu.SEMMainMenu;

public class SEMEventMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location EVENT = WEB_APP.join(EventController.PREFIX + "/");

    public static MenuNode eventAdmin = menu(SEMMainMenu.MAIN, "event"); // MAIN/Admin
    public static MenuNode event = menu(SEMMainMenu.MAIN, "event");

    static MenuNode categories = entry(eventAdmin, 10, "categories", getDictIndex(EventCategory.class));
    static MenuNode priorities = entry(eventAdmin, 11, "priorities", //
            WEB_APP.join(EventPriorityController.PREFIX).join("index.do"));

    static MenuNode states = entry(eventAdmin, 12, "states", getDictIndex(EventStatus.class));
    static MenuNode pendingTasks = entry(event, 10, "pendingTasks", EVENT.join("index.do?stereo=TSK&closed=false"));
    static MenuNode completedTasks = entry(event, 11, "completedTasks", EVENT.join("index.do?stereo=TSK&closed=true"));
    static MenuNode eventIndex = entry(event, 20, "eventIndex", EVENT.join("index.do?recent=30"));
    static MenuNode activityIndex = entry(event, 30, "activityIndex", EVENT.join("index.do?stereo=ACT&recent=30"));

    @Override
    protected void preamble() {
    }

}
