package com.bee32.sem.event;

import com.bee32.plover.orm.ext.dict.DictController;
import com.bee32.plover.orm.util.ITypeAbbrAware;
import com.bee32.plover.servlet.context.LocationContext;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventState;
import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMEventMenu
        extends MenuContribution
        implements ITypeAbbrAware {

    static LocationContext EVENT = WEB_APP.join(SEMEventModule.PREFIX);
    static LocationContext DICT = WEB_APP.join(DictController.PREFIX);

    @Contribution("sa")
    MenuEntry eventAdmin = new MenuEntry("event");

    @Contribution("sa/event")
    MenuEntry priorities = new MenuEntry(0, "priorities", DICT.join(ABBR.abbr(EventPriority.class) + "/index.htm"));

    @Contribution("sa/event")
    MenuEntry states = new MenuEntry(0, "states", DICT.join(ABBR.abbr(EventState.class) + "/index.htm"));

    @Contribution(".")
    MenuEntry event = new MenuEntry("event");

    @Contribution("event")
    MenuEntry pendingTasks = new MenuEntry(10, "pendingTasks");

    @Contribution("event")
    MenuEntry completedTasks = new MenuEntry(20, "completedTasks");

    @Contribution("event")
    MenuEntry eventIndex = new MenuEntry(100, "eventIndex");

    @Contribution("event")
    MenuEntry activityIndex = new MenuEntry(200, "activityIndex");

    @Override
    protected void preamble() {
    }

}
