package com.bee32.sem.event;

import java.util.Collection;

import com.bee32.plover.arch.util.EnumAlt;

/**
 * Event state.
 */
public abstract class EventState<$ extends EventState<$>>
        extends EnumAlt<Integer, $> {

    private static final long serialVersionUID = 1L;

    public static final int CLASS_MASK = 0xff000000;
    public static final int CLASS_SHIFT = 24;

    public static final int INDEX_MASK = 0x0000ffff;
    public static final int INDEX_SHIFT = 0;

    private final int index;

    protected EventState(int id, String name) {
        super(id, name);
        index = (id & INDEX_MASK) >> INDEX_SHIFT;
    }

    public int getIndex() {
        return index;
    }

    protected static int __class__(int classIndex) {
        return ((classIndex << CLASS_SHIFT) & CLASS_MASK);
    }

    @SuppressWarnings("unchecked")
    public static EventState<?> forName(String name) {
        return forName(EventState.class, name);
    }

    @SuppressWarnings("unchecked")
    public static Collection<? extends EventState<?>> values() {
        return values(EventState.class);
    }

    @SuppressWarnings("unchecked")
    public static EventState<?> forValue(Integer value) {
        return forValue(EventState.class, value);
    }

    public static EventState<?> forValue(int value) {
        return forValue(new Integer(value));
    }

}
