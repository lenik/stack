package com.bee32.sem.event;

import com.bee32.plover.orm.ext.dict.CommonDictController;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.context.Location;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.event.web.EventController;
import com.bee32.sem.event.web.EventPriorityController;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMEventMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static Location EVENT = WEB_APP.join(EventController.PREFIX);
    static Location DICT = WEB_APP.join(CommonDictController.PREFIX);

    @Contribution("sa")
    MenuEntry eventAdmin = new MenuEntry("event");

    @Contribution("sa/event")
    MenuEntry priorities = new MenuEntry(0, "priorities", //
            WEB_APP.join(EventPriorityController.PREFIX).join("index.htm"));

    @Contribution("sa/event")
    MenuEntry states = new MenuEntry(0, "states", //
            DICT.join(ABBR.abbr(EventStatus.class) + "/index.htm"));

    @Contribution(".")
    MenuEntry event = new MenuEntry("event");

    @Contribution("event")
    MenuEntry pendingTasks = new MenuEntry(10, "pendingTasks", EVENT.join("event/index.htm?cat=pending"));

    @Contribution("event")
    MenuEntry completedTasks = new MenuEntry(20, "completedTasks", EVENT.join("event/index.htm?cat=compl"));

    @Contribution("event")
    MenuEntry eventIndex = new MenuEntry(100, "eventIndex", EVENT.join("event/index.htm?recent=30"));

    @Contribution("event")
    MenuEntry activityIndex = new MenuEntry(200, "activityIndex", EVENT.join("event/index.htm?cat=act&recent=30"));

    @Override
    protected void preamble() {
    }

}
