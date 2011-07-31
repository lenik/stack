package com.bee32.sem.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.free.IllegalUsageException;

import com.bee32.plover.arch.service.ServicePrototypeLoader;
import com.bee32.plover.arch.util.EnumAlt;

public class EventState
        extends EnumAlt<Integer, EventState> {

    private static final long serialVersionUID = 1L;

    public static final int SEL_MASK = 0xff000000;
    public static final int SEL_EVENT = 0x01000000;
    public static final int SEL_ACTIVITY = 0x02000000;
    public static final int SEL_TASK = 0x04000000;

    public static final int CLASS_MASK = 0x000ff000;
    public static final int CLASS_SHIFT = 12;

    public EventState(int id, String name) {
        super(id, name);
    }

    static Map<String, EventState> nameMap = new HashMap<String, EventState>();
    static Map<Integer, EventState> valueMap = new HashMap<Integer, EventState>();

    @Override
    protected Map<String, EventState> getNameMap() {
        return nameMap;
    }

    @Override
    protected Map<Integer, EventState> getValueMap() {
        return valueMap;
    }

    public boolean isEventRelated() {
        return (value & SEL_EVENT) != 0;
    }

    public boolean isActivityRelated() {
        return (value & SEL_ACTIVITY) != 0;
    }

    public boolean isTaskRelated() {
        return (value & SEL_TASK) != 0;
    }

    protected static int __class__(int sel, int classIndex) {
        return (sel & SEL_MASK) | ((classIndex << CLASS_SHIFT) & CLASS_MASK);
    }

    public static EventState forName(String altName) {
        EventState definedState = nameMap.get(altName);
        if (definedState != null)
            return definedState;

        throw new IllegalUsageException("Invalid state: " + altName);
    }

    public static EventState valueOf(int altId) {
        EventState definedState = valueMap.get(altId);
        if (definedState != null)
            return definedState;

        String idHex = Integer.toHexString(altId);
        throw new IllegalUsageException("Invalid state: 0x" + idHex);
    }

    public static List<Integer> list(int mask) {
        List<Integer> list = new ArrayList<Integer>();
        for (EventState state : valueMap.values()) {
            if ((state.value & mask) != 0)
                list.add(state.value);
        }
        return list;
    }

    public static List<Integer> listFor(int _class) {
        List<Integer> list = new ArrayList<Integer>();
        for (EventState state : valueMap.values()) {
            if ((state.value & CLASS_MASK) >> CLASS_SHIFT == _class)
                list.add(state.value);
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
