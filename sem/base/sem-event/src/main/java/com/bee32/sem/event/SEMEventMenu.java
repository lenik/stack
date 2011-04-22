package com.bee32.sem.event;

import com.bee32.sem.frame.Contribution;
import com.bee32.sem.frame.menu.MenuContribution;
import com.bee32.sem.frame.menu.MenuEntry;

public class SEMEventMenu
        extends MenuContribution {

    @Contribution("sa")
    MenuEntry eventAdmin = new MenuEntry("event");

    @Contribution("sa/event")
    MenuEntry priorities = new MenuEntry(0, "priorities");

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
