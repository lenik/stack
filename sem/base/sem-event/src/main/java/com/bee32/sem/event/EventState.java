package com.bee32.sem.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.bee32.plover.servlet.context.Location;
import com.bee32.plover.servlet.context.Locations;

public class EventState {

    public static final int GROUP_EVENT = 1;
    public static final int GROUP_ACTIVITY = 2;
    public static final int GROUP_TASK = 4;

    private final int groups;
    private final int index;
    private String name;
    private LocationContext icon;

    static Map<Integer, EventState> all = new HashMap<Integer, EventState>();

    public EventState(int groups, int index, String name) {
        this.groups = groups;
        this.index = index;
        this.name = name;

        all.put(index, this);
    }

    public static EventState get(int index) {
        return all.get(index);
    }

    public static List<EventState> getAll(int groupMask) {
        List<EventState> list = new ArrayList<EventState>();

        for (EventState state : all.values()) {
            if ((state.groups & groupMask) != 0)
                list.add(state);
        }
        return list;
    }

    public int getGroups() {
        return groups;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationContext getIcon() {
        return icon;
    }

    public void setIcon(LocationContext icon) {
        this.icon = icon;
    }

    public static final EventState UNKNOWN = new EventState(GROUP_EVENT, 0, "unknown");
    public static final EventState RUNNING = new EventState(GROUP_EVENT, 1, "running");
    public static final EventState SUSPENDED = new EventState(GROUP_EVENT, 2, "suspended");
    public static final EventState CANCELED = new EventState(GROUP_EVENT, 3, "canceled");
    public static final EventState DONE = new EventState(GROUP_EVENT, 4, "done");
    public static final EventState FAILED = new EventState(GROUP_EVENT, 5, "failed");
    public static final EventState ERRORED = new EventState(GROUP_EVENT, 6, "errored");

}
