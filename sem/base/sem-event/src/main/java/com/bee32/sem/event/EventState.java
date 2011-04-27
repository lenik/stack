package com.bee32.sem.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.servlet.context.Location;
import com.bee32.plover.servlet.context.Locations;

public class EventState {

    public static final int SEL_MASK = 0xff000000;
    public static final int SEL_EVENT = 0x01000000;
    public static final int SEL_ACTIVITY = 0x02000000;
    public static final int SEL_TASK = 0x04000000;

    public static final int CLASS_MASK = 0x000ff000;
    public static final int CLASS_SHIFT = 12;

    private final int id;
    private final String name;
    private final String displayName;
    private final Location icon;

    static Map<Integer, EventState> all = new HashMap<Integer, EventState>();

    public EventState(int id, String name) {
        this.id = id;
        this.name = name;

        this.displayName = _nls(name + ".displayName", name);

        String icon = _nls(name + ".icon", null);
        if (icon != null)
            this.icon = Locations.parse(icon);
        else
            this.icon = null;

        all.put(id, this);
    }

    protected String _nls(String key, String def) {
        ResourceBundle rb = ResourceBundle.getBundle(getClass().getName());

        if (rb.containsKey(key))
            return rb.getString(key);
        else
            return def;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Location getIcon() {
        return icon;
    }

    public boolean isEventRelated() {
        return (id & SEL_EVENT) != 0;
    }

    public boolean isActivityRelated() {
        return (id & SEL_ACTIVITY) != 0;
    }

    public boolean isTaskRelated() {
        return (id & SEL_TASK) != 0;
    }

    protected static int __class__(int sel, int facility) {
        return (sel & SEL_MASK) | ((facility << CLASS_SHIFT) & CLASS_MASK);
    }

    public static EventState get(int id) {
        return all.get(id);
    }

    public static List<Integer> list(int mask) {
        List<Integer> list = new ArrayList<Integer>();
        for (EventState state : all.values()) {
            if ((state.id & mask) != 0)
                list.add(state.id);
        }
        return list;
    }

    public static List<Integer> listFor(int _class) {
        List<Integer> list = new ArrayList<Integer>();
        for (EventState state : all.values()) {
            if ((state.id & CLASS_MASK) >> CLASS_SHIFT == _class)
                list.add(state.id);
        }
        return list;
    }

    static {
        try {
            ServicePrototypeLoader.load(EventState.class);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
